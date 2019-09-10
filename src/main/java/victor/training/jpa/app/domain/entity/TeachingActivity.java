package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "TYPE")
public abstract class TeachingActivity  extends AbstractEntity  {

	@ManyToOne
	@JoinColumn(name = "S_ID")
	private Subject subject;

	@Embedded
	private TimeSlot timeSlot;

	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;

	@ManyToMany(mappedBy = "activities")
	private Set<Teacher> teachers = new HashSet<>();

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	
	
}
