package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

//@Data
@Entity
public class Teacher  extends AbstractEntity  {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}

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

	@AttributeOverrides({
		@AttributeOverride(name = "day", column = @Column(name = "COUNSELING_DAY"))
			// to be continued...
	})
	@Embedded
	private TimeSlot timeSlot = new TimeSlot();
	
	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
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
}
