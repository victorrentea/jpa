package victor.training.jpa.app.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.GradeConverter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;
import victor.training.jpa.app.facade.dto.TimeSlotDto;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

@Getter
@Setter

@Entity
@SequenceGenerator(name = "mySeqGeneration")
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
	@GeneratedValue(generator = "mySeqGeneration")
	private Long id; // PK

//	@NotNull
//	@TeacherName // a custom validation composed of @NotNUll and Size(min=3,max=20)
//	@Setter(AccessLevel.NONE)
	private String name;

//@Enumerated(EnumType.STRING)
	@Convert(converter = GradeConverter.class)
	private Grade grade;

	@OneToOne(cascade = CascadeType.ALL)
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;

//	@OneToMany
	@ElementCollection
	@JoinColumn // without this a table is born
//	@OrderColumn
	@OrderBy("value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(mappedBy = "teachers")
	@Setter(AccessLevel.NONE)
	Set<TeachingActivity> activities = new HashSet<>();


	public Set<TeachingActivity> getActivities() {
		return Collections.unmodifiableSet(activities);
	}

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

	public Teacher() { // only for hibernate
	}
	//why hibernate requires protected constructor?
	public Teacher(String name) {
		this.name = Objects.requireNonNull(name);
//		Validator validator = hocus pocus..;
//		validator.validate((this));
	}

	@Override
	public String toString() {
		return "Teacher{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 '}';
	}
}
