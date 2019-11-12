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
public abstract class TeachingActivity {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID")
	private Subject subject;

	@Embedded
	private TimeSlot timeSlot = new TimeSlot();

	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;

	@ManyToMany
	@JoinTable(name="TEACHERS_ACTIVITieS")
	private Set<Teacher> teachers = new HashSet<>();
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public DayOfWeek getDay() {
		return timeSlot.getDay();
	}

	public void setDay(DayOfWeek day) {
		timeSlot.setDay(day);
	}

	public void setStartHour(Integer startHour) {
		timeSlot.setStartHour(startHour);
	}

	public Integer getDurationInHours() {
		return timeSlot.getDurationInHours();
	}

	public void setDurationInHours(Integer durationInHours) {
		timeSlot.setDurationInHours(durationInHours);
	}

	public String getRoomId() {
		return timeSlot.getRoomId();
	}

	public void setRoomId(String roomId) {
		timeSlot.setRoomId(roomId);
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	
	
}
