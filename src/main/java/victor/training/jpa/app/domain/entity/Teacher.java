package victor.training.jpa.app.domain.entity;

import org.hibernate.validator.constraints.Length;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Documented
//@Constraint(validatedBy = TeacherNameValidator.class)
//@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
//@Retention(RUNTIME)
//@interface TeacherName {
//
//}
//
//class TeacherNameValidator implements ConstraintValidator<TeacherName, String> {
//
//	@Override
//	public void initialize(TeacherName constraintAnnotation) {
//
//	}
//
//	@Override
//	public boolean isValid(String value, ConstraintValidatorContext context) {
//		return value.equals(value.toUpperCase());
//	}
//}

@Entity
@Table(uniqueConstraints =
	@UniqueConstraint(name = "UNIQUE_DETAILS_ID", columnNames = {"DETAILS_ID"})
)
@NamedQueries({
		@NamedQuery(name = "Teacher.fetchChannels",
				query = "FROM Teacher t LEFT JOIN FETCH t.channels WHERE t.id=:id")
})
public class Teacher {
//	interface DraftMode {}
//	interface SubmittedMode {}

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}

	@Id
	@GeneratedValue
	private Long id;

//	@Length(max = 5/*, groups = DraftMode.class*/)
//	@TeacherName
	private String name;
	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "DETAILS_ID")
	private TeacherDetails details;

	@ElementCollection
	@CollectionTable(name = "TEACHER_CONTACT_CHANNELS")
	@OrderBy("type, value")
	private List<ContactChannel> channels = new ArrayList<>();
	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;

	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "day", column = @Column(name = "COUNSELING_DAY"))
	})
	private TimeSlot timeSlot = new TimeSlot();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="TEACHER_ID")
	private List<Periuta> periute = new ArrayList<>();

	public List<Periuta> getPeriute() {
		return periute;
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

	public DayOfWeek getCounselingDay() {
		return timeSlot.getDay();
	}

	public Integer getCounselingStartHour() {
		return timeSlot.getStartHour();
	}

	public Integer getCounselingDurationInHours() {
		return timeSlot.getDurationInHours();
	}

	public String getCounselingRoomId() {
		return timeSlot.getRoomId();
	}

	public List<ContactChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ContactChannel> channel) {
		this.channels = channel;
	}

	public Set<Subject> getHeldSubjects() {
		return Collections.unmodifiableSet(heldSubjects);
	}

	public void addSubject(Subject subject) {
	    heldSubjects.add(subject);
	    subject.setHolderTeacher(this);
    }

	public void setHeldSubjects(Set<Subject> heldSubjects) {
		this.heldSubjects = heldSubjects;
	}








}
