package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private Grade grade;
	
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
	
	@Embedded
	private CounselingTime counselingTime;

	public Teacher setCounselingTime(CounselingTime counselingTime) {
		this.counselingTime = counselingTime;
		return this;
	}

	public CounselingTime getCounselingTime() {
		return counselingTime;
	}

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Set<TeachingActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<TeachingActivity> activities) {
		this.activities = activities;
	}

	public List<ContactChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ContactChannel> channel) {
		this.channels = channel;
	}

	public Set<Subject> getHeldSubjects() {
		return heldSubjects;
	}

	public void setHeldSubjects(Set<Subject> heldSubjects) {
		this.heldSubjects = heldSubjects;
	}
	
	
	
	

	
	

}
