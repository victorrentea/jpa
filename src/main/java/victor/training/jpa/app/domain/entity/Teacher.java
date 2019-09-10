package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

//@Data
@Entity
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}

	@Id
	private Long id;
	
	private String name;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToOne
	@JoinColumn(name = "TD_ID")
	private TeacherDetails details;
	
	//	Order Column(name="INDEX")
	// Order By "type ASC, value ASC"

	@ElementCollection
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")

	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany
	@JoinTable(name = "TEACHER_ACTIVITY",
		joinColumns = @JoinColumn(name = "TEACHER_ID"),
		inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID")
	)
	private Set<TeachingActivity> activities = new HashSet<>();
	
	private DayOfWeek counselingDay;
	
	private int counselingStartHour;
	
	private int counselingDurationInHours;
	
	private String counselingRoomId;
	
	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	
	

	
	

}
