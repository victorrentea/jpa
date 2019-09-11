package victor.training.jpa.app.domain.entity;

import java.util.*;

import javax.persistence.*;

import static java.util.Collections.unmodifiableSet;

//@Data
@Entity
public class Teacher  extends AbstractEntity  {

    public void removeSubject(Subject subject) {
        heldSubjects.remove(subject);
        subject.setHolderTeacher(null);
    }

    public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}

	private String name;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TD_ID")
	private TeacherDetails details;
	
	//	Order Column(name="INDEX")
	// Order By "type ASC, value ASC"

	@ElementCollection
//    @OrderBy("value ASC")
    @OrderColumn(name = "indexul_meu")
	private List<ContactChannel> channels = new ArrayList<>();

    public List<ContactChannel> getChannels() {
        return channels;
    }

    @OneToMany(mappedBy = "holderTeacher",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "TEACHER_ACTIVITY",
		joinColumns = @JoinColumn(name = "TEACHER_ID"),
		inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID")
	)
	private Set<TeachingActivity> activities = new HashSet<>();

	@AttributeOverrides({
		@AttributeOverride(name = "day", column = @Column(name = "COUNSELING_DAY"))
			// to be continued...
	})
	@Embedded
	private TimeSlot timeSlot = new TimeSlot();

	@OneToOne
	private Frigider frigider;

	public Teacher() {
	}

	public Set<TeachingActivity> getActivities() {
		return unmodifiableSet(activities);
	}

	public void addActivity(TeachingActivity activity) {
		activities.add(activity);
		activity.teachers.add(this);
	}

	public Set<Subject> getHeldSubjects() {
		return unmodifiableSet(heldSubjects);
	}

	public void addSubject(Subject subject) {
		heldSubjects.add(subject); // adica asta e degeaba ?
		subject.setHolderTeacher(this);
	}

	public Teacher(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TeacherDetails getDetails() {
		return details;
	}

	public void setDetails(TeacherDetails details) {
		this.details = details;
	}

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setChannels(List<ContactChannel> channels) {
        this.channels = channels;
    }

    public void setHeldSubjects(Set<Subject> heldSubjects) {
        this.heldSubjects = heldSubjects;
    }

    public void setActivities(Set<TeachingActivity> activities) {
        this.activities = activities;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Frigider getFrigider() {
        return frigider;
    }

    public void setFrigider(Frigider frigider) {
        this.frigider = frigider;
    }
}
