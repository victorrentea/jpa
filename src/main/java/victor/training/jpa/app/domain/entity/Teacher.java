package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
		LECTURER, PROFESSOR, CONF, ASSISTANT
	}
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private Grade grade;
	
	// fetch=LAZY or invert the link to retrieve details by teacher via repo
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private TeacherDetails details;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
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
	private TimeSlot counselingTimeSlot = new TimeSlot();

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

}
