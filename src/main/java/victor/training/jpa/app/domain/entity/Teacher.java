package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;


@Entity
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}


	@Getter @Setter
	@Id 	@GeneratedValue
	private Long id;
	@Getter @Setter
	private String name;
	@Getter @Setter
	@Enumerated(EnumType.STRING)
	private Grade grade;
	@Getter @Setter
	@OneToOne
	private TeacherDetails details;

	@ElementCollection
	private List<ContactChannel> channels = new ArrayList<>();
	@Setter
	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;
	@Getter @Setter
	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();
	@Getter @Setter
	@Embedded
	private TimeSlot counselingTime = new TimeSlot();


	public Iterable<ContactChannel> getChannels() {
		return channels;
	}
	public void addChannel(ContactChannel contactChannel) {
		channels.add(contactChannel);
	}

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public Iterable<Subject> getHeldSubjects() {
		return heldSubjects;
	}

	public void addHeldSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
	}

}
