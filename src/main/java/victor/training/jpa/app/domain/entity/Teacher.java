package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@SequenceGenerator(name="TeacherSeq", sequenceName = "TEACHER_SEQ")
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTANT
	}
	
	@Id
	@GeneratedValue(generator = "TeacherSeq")
	private Long id;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private Grade grade;
	
	// fetch=LAZY or invert the link to retrieve details by teacher via repo
	@OneToOne(cascade = ALL)
	private TeacherDetails details;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	@ManyToMany(mappedBy = "teachers")
	Set<TeachingActivity> activities = new HashSet<>();

	@Embedded
	private TimeSlot counselingSlot;

	public Teacher() {
	}

	public Set<TeachingActivity> getActivities() {
		return Collections.unmodifiableSet(activities);
	}

	public Teacher(String name) {
		this.name = name;
	}

}
