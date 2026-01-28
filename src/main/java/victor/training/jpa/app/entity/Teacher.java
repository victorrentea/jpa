package victor.training.jpa.app.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import java.time.DayOfWeek;
import java.util.*;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.EAGER;


@Entity
@Data // ANATHEMA! NICIODATA LA BIROU!
public class Teacher implements Auditable {
//	transient boolean chiarDateDoarTemporarAici;
//	public boolean isCanCurrentUserApproveLoan() {
	// penibil. cand @Entity pleaca JSON la FE
//	}

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
	@GeneratedValue // din seq(aici), IDENTITY
	private Long id;

	@Column(unique = true)
	@Size(min=2) // min 2 char sau null
	private String name;

	// validari composite 2+ campuri

	@AssertTrue(message = "Daca are grade, trebuie sa aiba si nume")
	public boolean isTrebuieSaAibaNumeDacaAreGrade() {
		return grade == null || (name != null && !name.isBlank());
	}

	@Embedded // ❤️adauga in tabela TEACHER campurile obiectului
	// sa modifici numele campurilor preluate
	@AttributeOverride(name = "lastModifiedBy", column = @Column(name = "lmb"))
	@Valid// cand validezi Teacher, valideaza si acest obiect inauntru
//	@NotNull
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
	@OneToOne(cascade = CascadeType.ALL)
	// daca moare parintele, sa moara si copiii
	private TeacherDetails details;
	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;

	public Teacher addHeldSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
		return this;
	}

//	@OneToMany
	@ElementCollection(fetch = EAGER)
	@OrderColumn(name="INDEX") // sa stochezi in DB ordinea customizata de user
//	@OrderBy("type ASC, value ASC")// la incarcarea din DB ii pre-sorteaza in lista dupa
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher",
			fetch = EAGER)// DE EVITAT! pt ca mereu va incarca copiii astia, chiar daca in 80% din cazuri nu ii folosesti
	// pui doar daca "Părintele n-are sens fără copii niciodată"
	// eg: class Retur{ fetch=EAGER List<ReturLine> lines; }
	private Set<Subject> heldSubjects = new HashSet<>() ;

	public Set<Subject> getHeldSubjects() {
		// hibernate foloseste reflection sa-ti citeasca campurile direct
		return Collections.unmodifiableSet(heldSubjects);
	}

	@ManyToMany(mappedBy = "teachers", fetch = EAGER)
	private List<TeachingActivity> activities = new ArrayList<>();

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

}
