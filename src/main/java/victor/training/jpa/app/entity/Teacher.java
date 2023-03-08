package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@Enumerated(EnumType.STRING)
//	@Convert(converter = GradeConverter.class)
	private Grade grade;
	
	// fetch=LAZY or invert the link to retrieve details by teacher via repo
	@OneToOne(cascade = CascadeType.ALL
		)
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher") // the field name of the other end of a <-> BIDIRECTIONAL relationship
	// bidirectional link are BAD!
	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	@ManyToMany(mappedBy = "teachers")
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
