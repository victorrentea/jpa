package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.GradeConverter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;
import victor.training.jpa.app.facade.dto.TimeSlotDto;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;

@Getter
@Setter

@Entity
public class Teacher {

	public enum Grade {
		LECTURER("L"), // 0
		PROFESSOR("P"), // 1
		CONF("C"),
		ASSISTANT("A");

		public final String dbValue;
		Grade(String dbValue) {
			this.dbValue = dbValue;
		}
	}

	// TODO ORM
	@Id
	@GeneratedValue
	private Long id; // PK

	private String name;

	// TODO ORM
//@Enumerated(EnumType.STRING)
	@Convert(converter = GradeConverter.class)
	private Grade grade;

	@OneToOne
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;

	// TODO ORM + sorted, ordered
	private List<ContactChannel> channels = new ArrayList<>();

	// TODO ORM
	private Set<Subject> heldSubjects = new HashSet<>() ;

	// TODO ORM
	private Set<TeachingActivity> activities = new HashSet<>();
	
	@Enumerated(EnumType.STRING)
	private DayOfWeek counselingDay;

	private Integer counselingStartHour;

	private Integer counselingDurationInHours;

	private String counselingRoomId;

//	@Embedded
//	private TimeSlot counseling;

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public String toString() {
		return "Teacher{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 '}';
	}
}
