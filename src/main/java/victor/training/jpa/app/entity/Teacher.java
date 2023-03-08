package victor.training.jpa.app.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Teacher {

	public Teacher approve(String username) throws RuntimeException {
		if (status != Status.DRAFT) {
			throw new IllegalArgumentException();
		}
		status = Status.APPROVED;
		approvalDate = LocalDate.now();
		approvalUser = username;
		return this;
	}

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
	
//	@PrePersist // like an Aspect
//	@PreUpdate
//	public void fixBidirectionals() {
//		System.out.println("RUNS?");
//		for (Subject heldSubject : heldSubjects) {
//			heldSubject.setHolderTeacher(this);
//		}
//	}

//	@LastModifiedBy
//	private String lastChangedBy; // spring sec will put the principal name from SecurityContextHolder here at every flush of dirty changes
//	@LastModifiedDate
//	private LocalDateTime lastChangedDate;

	public enum Status {
		DRAFT,
		APPROVED,
		REJECTED,
		SUBMITTED,
		DELETE,
	}

	@Setter(AccessLevel.NONE)
	@Enumerated(EnumType.STRING)
	private Status status = Status.DRAFT;

	@Setter(AccessLevel.NONE)
	private LocalDate approvalDate;
	@Setter(AccessLevel.NONE)
	private String approvalUser;
	
	@Id
	@GeneratedValue
	private Long id;

	// #1 DB contraint
	@Column(nullable = false) // NOT NULL on the column is better than just @NotNull (javax.validation) < protects the DB against rogue/stupid scripts or manual Fri night patches to prod

	// #2 javax.validation
	@Size(min = 3)
	private String name;

	// #3 The model blocks the inconsistent state (the simplest Java solution, least magic)
	protected Teacher() { // for Hibernate's eyes only; no developers can call it from app code
	}

	public Teacher(String name) {
		setName(name);
	}

	public Teacher setName(String name) {
		if (name.length() < 3) {
			throw new IllegalArgumentException(" name to small");
		}
		this.name = name;
		return this;
	}

	@Enumerated(EnumType.STRING)
//	@Convert(converter = GradeConverter.class)
	private Grade grade;
	
	// fetch=LAZY or invert the link to retrieve details by teacher via repo
	@OneToOne(cascade = CascadeType.ALL
		)
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher") // the field name of the other end of a <-> BIDIRECTIONAL relationship
	// bidirectional link are BAD!
	@Setter(AccessLevel.NONE)
	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	@ManyToMany(mappedBy = "teachers")
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
	private TimeSlot counseling;


	public Set<Subject> getHeldSubjects() {
		return Collections.unmodifiableSet(heldSubjects);
	}

	public void addSubject(Subject subject) { // please welcome OOP: "encapsulate collection" refactoring 24 years old stuff
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
	}

	@Override
	public String toString() {
		return "Teacher{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 '}';
	}
}
