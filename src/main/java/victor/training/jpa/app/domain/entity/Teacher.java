package victor.training.jpa.app.domain.entity;

import java.util.*;

import javax.persistence.*;

import static java.util.Collections.unmodifiableSet;

//@Data
@Entity
public class Teacher  extends AbstractEntity  {


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
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher",cascade = CascadeType.ALL)
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany
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
	
	public Teacher() {
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
}
