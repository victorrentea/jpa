package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

//@Data
@Entity
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}

	@Id
	private Long id;
	
	private String name;
	
	private Grade grade;
	
//	private TeacherDetails details;
	
	//	Order Column(name="INDEX")
	// Order By "type ASC, value ASC"
//	private List<ContactChannel> channels = new ArrayList<>();

//	private Set<Subject> heldSubjects = new HashSet<>() ;
	
//	private Set<TeachingActivity> activities = new HashSet<>();
	
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
