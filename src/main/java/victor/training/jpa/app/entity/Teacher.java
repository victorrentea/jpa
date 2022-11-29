package victor.training.jpa.app.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Teacher {

	public void addSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
	}

	public enum Grade {
		LECTURER("L"),
		PROFESSOR("P"),
		CONF("C"),
		ASSISTANT("A");

		public final String dbValue;
		Grade(String dbValue) {
			this.dbValue = dbValue;
		}
	}
	
	@Id
	@GeneratedValue
	private Long id;

	@Setter
	private String name;
	
	@Enumerated(EnumType.STRING)
//	@Convert(converter = GradeConverter.class)
	private Grade grade;
	
	// fetch=LAZY or invert the link to retrieve details by teacher via repo
	// a 👻 proxy is placed in this field, triggering network call when you access anything else than the ID of the TeacherDetails
	@OneToOne(fetch = FetchType.LAZY)
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	@Setter(AccessLevel.NONE)
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();

	@Embedded
	private TimeSlot counseling;

//	@Enumerated(EnumType.STRING)
//	private DayOfWeek counselingDay;
//
//	private Integer counselingStartHour;
//
//	private Integer counselingDurationInHours;
//
//	private String counselingRoomId;

	public Set<Subject> getHeldSubjects() {
		return Collections.unmodifiableSet(heldSubjects);
	}

	//	@Embedded
//	private TimeSlot counseling;

	public Teacher() {
	}

	public Teacher(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher{" +
			   "id=" + id +
			   ", name='" + name + '\'' +
			   '}';
	}
}
