package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints =
	@UniqueConstraint(name = "UNIQUE_DETAILS_ID", columnNames = {"DETAILS_ID"})
)
@NamedQueries({
		@NamedQuery(name = "Teacher.fetchChannels", query = "FROM Teacher t LEFT JOIN FETCH t.channels WHERE t.id=:id")
})
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DETAILS_ID")
	private TeacherDetails details;

	@ElementCollection
	@CollectionTable(name = "TEACHER_CONTACT_CHANNELS")
	@OrderBy("type, value")
	private List<ContactChannel> channels = new ArrayList<>();
	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "day", column = @Column(name = "COUNSELING_DAY"))
	})
	private TimeSlot timeSlot = new TimeSlot();

	public Teacher() {
	}

	public Teacher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<TeachingActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<TeachingActivity> activities) {
		this.activities = activities;
	}

	public DayOfWeek getCounselingDay() {
		return timeSlot.getDay();
	}

	public Integer getCounselingStartHour() {
		return timeSlot.getStartHour();
	}

	public Integer getCounselingDurationInHours() {
		return timeSlot.getDurationInHours();
	}

	public String getCounselingRoomId() {
		return timeSlot.getRoomId();
	}

	public List<ContactChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ContactChannel> channel) {
		this.channels = channel;
	}

	public Set<Subject> getHeldSubjects() {
		return Collections.unmodifiableSet(heldSubjects);
	}

	public void addSubject(Subject subject) {
	    heldSubjects.add(subject);
	    subject.setHolderTeacher(this);
    }

	public void setHeldSubjects(Set<Subject> heldSubjects) {
		this.heldSubjects = heldSubjects;
	}








}
