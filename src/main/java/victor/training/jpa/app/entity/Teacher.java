package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import java.time.DayOfWeek;
import java.util.*;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
public class Teacher implements Auditable {

	public enum Grade {
		LECTURER("L"),
		PROFESSOR("P"),
		CONF("C"),
		ASSISTANT("A");

		public final String dbValue;

		Grade(String dbValue) {
			this.dbValue = dbValue;
		}
	}
	@Id
	@GeneratedValue
	private Long id;

	private String name;


	@Embedded // ❤️adauga in tabela TEACHER campurile obiectului
	// sa modifici numele campurilor preluate
	@AttributeOverride(name = "lastModifiedBy", column = @Column(name = "lmb"))
	private AuditedEntity auditedEntity;

	//+ spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
	// atributele vor fi prefixate cu "origin_" respectiv "destination_"
//	@Embedded
//	private Point origin;
//	@Embedded
//	private Point destination;


	public AuditedEntity getAuditedEntity() {
		return auditedEntity;
	}

	public void setAuditedEntity(AuditedEntity auditedEntity) {
		this.auditedEntity = auditedEntity;
	}

	@Enumerated(EnumType.STRING)
//	@Convert(converter = GradeConverter.class)
	private Grade grade;

	// fetch=LAZY or invert the link to retrieve details by teacher via repo

	@OneToOne
	private TeacherDetails details;
	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;

	public void addHeldSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
	}

	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;

	public Set<Subject> getHeldSubjects() {
		// hibernate foloseste reflection sa-ti citeasca campurile direct
		return Collections.unmodifiableSet(heldSubjects);
	}

	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();

	@Enumerated(EnumType.STRING)
	private DayOfWeek counselingDay;

	private Integer counselingStartHour;

	private Integer counselingDurationInHours;

	private String counselingRoomId;

//	@Embedded
//	private TimeSlot counseling;

	public Teacher() {
	}

	public Teacher(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
