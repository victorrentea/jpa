package victor.training.jpa.app.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // de ce nu MappedSuperclass ?
@DiscriminatorColumn(name = "DISCR")
public abstract class TeachingActivity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID")
	private Subject subject;

	@Embedded
	private CalendarEntry calendarEntry = new CalendarEntry();

	private String details;

	private LocalDateTime lastModifiedDate;

	private String lastModifiedBy;

	@ManyToMany(mappedBy = "activities")
	private Set<Teacher> teachers = new HashSet<>();

	void setSubject(Subject subject) {
		this.subject = subject;
	}

	public TeachingActivity setCalendarEntry(CalendarEntry calendarEntry) {
		this.calendarEntry = calendarEntry;
		return this;
	}

	public String getDetails() {
		return details;
	}

	public TeachingActivity setDetails(String details) {
		this.details = details;
		return this;
	}
}
