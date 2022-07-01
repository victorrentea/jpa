package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;
import victor.training.jpa.app.facade.dto.TimeSlotDto;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter

// TODO ORM
public class Teacher {

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

	// TODO ORM
	private Long id;

	private String name;

	// TODO ORM
	private Grade grade;

	// TODO ORM
	private TeacherDetails details;

	// TODO ORM
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
