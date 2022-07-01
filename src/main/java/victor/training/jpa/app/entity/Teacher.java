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

	@Id
	@GeneratedValue
	private Long id; // PK

	private String name;

//@Enumerated(EnumType.STRING)
	@Convert(converter = GradeConverter.class)
	private Grade grade;

	@OneToOne
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;

	@OneToMany // MISTAKE
	@JoinColumn // without this a table is born
//	@OrderColumn
	@OrderBy("value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();
	
//	@Enumerated(EnumType.STRING)
//	private DayOfWeek counselingDay;
//
//	private Integer counselingStartHour;
//
//	private Integer counselingDurationInHours;
//
//	private String counselingRoomId;
	@Embedded
	private TimeSlot counseling;

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
