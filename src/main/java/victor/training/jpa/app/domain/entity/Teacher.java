package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

import javax.persistence.*;

import static java.util.Collections.*;
import static javax.persistence.CascadeType.PERSIST;

@Data
@Entity
public class Teacher {
	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT;
	}
	@Id
	@EqualsAndHashCode.Exclude
	@GeneratedValue
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToOne
	@JoinColumn(name = "TD_ID", foreignKey = @ForeignKey(name = "FK_TEACHER_DETAILS"))
	private TeacherDetails details;


//	@OneToMany
	@ElementCollection // DOAR PENTRU COLECTII MICI:2-10 elem. atentie: la orice remove, se reinsera toate -1.
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher", cascade = PERSIST)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany
	@JoinTable(name = "TEACHER_ACTIVITIES",
		joinColumns = @JoinColumn(name = "TEACHER_ID"),
		inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID"))
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
	@AttributeOverrides({
		@AttributeOverride(name = "day", column = @Column(name = "COUNSELING_DAY")),
		@AttributeOverride(name = "durationInHours", column = @Column(name = "COUNSELING_DURATION_IN_HOURS")),
		@AttributeOverride(name = "startHour", column = @Column(name = "COUNSELING_START_HOUR")),
		@AttributeOverride(name = "roomId", column = @Column(name = "COUNSELING_ROOM_ID"))
	})
	private CalendarEntry counselingCalendarEntry = new CalendarEntry();



//
//	public Teacher() {
//	}
//
//	public Teacher(String name) {
//		this.name = name;
//	}
//
//	public Iterable<Subject> getHeldSubjects() {
//		return heldSubjects;
//	}

	public Teacher addSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
		return this;
	}

	public Set<Subject> getHeldSubjects() {
		return unmodifiableSet(heldSubjects);
	}
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public TeacherDetails getDetails() {
//		return details;
//	}
//
//	public void setDetails(TeacherDetails details) {
//		this.details = details;
//	}
//
//	public Grade getGrade() {
//		return grade;
//	}
//
//	public void setGrade(Grade grade) {
//		this.grade = grade;
//	}
//
//	public Set<TeachingActivity> getActivities() {
//		return activities;
//	}
//
//	public void setActivities(Set<TeachingActivity> activities) {
//		this.activities = activities;
//	}
//
//	public DayOfWeek getCounselingDay() {
//		return counselingDay;
//	}
//
//	public void setCounselingDay(DayOfWeek counselingDay) {
//		this.counselingDay = counselingDay;
//	}
//
//	public Integer getCounselingStartHour() {
//		return counselingStartHour;
//	}
//
//	public void setCounselingStartHour(Integer counselingStartHour) {
//		this.counselingStartHour = counselingStartHour;
//	}
//
//	public Integer getCounselingDurationInHours() {
//		return counselingDurationInHours;
//	}
//
//	public void setCounselingDurationInHours(Integer counselingDurationInHours) {
//		this.counselingDurationInHours = counselingDurationInHours;
//	}
//
//	public String getCounselingRoomId() {
//		return counselingRoomId;
//	}
//
//	public void setCounselingRoomId(String counselingRoomId) {
//		this.counselingRoomId = counselingRoomId;
//	}
//
//	public List<ContactChannel> getChannels() {
//		return channels;
//	}
//
//	public void setChannels(List<ContactChannel> channel) {
//		this.channels = channel;
//	}
//
//	public Set<Subject> getHeldSubjects() {
//		return heldSubjects;
//	}
//
//	public void setHeldSubjects(Set<Subject> heldSubjects) {
//		this.heldSubjects = heldSubjects;
//	}
//
//
//
//
//
//
//

}
