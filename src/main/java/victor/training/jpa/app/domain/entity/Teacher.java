package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;


@Entity
public class Teacher {

	public void removeSubject(Subject subject) {
		heldSubjects.remove(subject);
//		subject.setHolderTeacher(null);
	}

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}
	@Getter @Setter
	@Id 	@GeneratedValue
	private Long id;
	@Getter @Setter
	@Basic(fetch = FetchType.LAZY)
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
	@OneToMany(mappedBy = "holderTeacher",cascade = CascadeType.PERSIST, orphanRemoval = true)  // NEVER use fetch=EAGER
	@OrderColumn
	private List<Subject> heldSubjects = new ArrayList<>() ;
	@Getter @Setter
	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();
	@Getter @Setter
	@Embedded
	private TimeSlot counselingTime = new TimeSlot();


	public Iterable<ContactChannel> getChannels() {
		return channels;
	}
	public Teacher addChannel(ContactChannel contactChannel) {
		channels.add(contactChannel);
		return this;
	}

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public List<Subject> getHeldSubjects() {
		return heldSubjects;//.stream().sorted(Subjcet::getMyIndex).collect(Collectors.toList());
	}

	public Teacher addHeldSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
		return this;
	}

}
