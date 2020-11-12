package victor.training.jpa.app.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Data
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
	private TeacherDetails details;

	@ElementCollection
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();

	@Embedded
	private TimeSlot counselingTime;

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

}
