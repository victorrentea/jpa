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
@DiscriminatorColumn(name = "DISCR")
@EntityListeners(AuditingEntityListener.class)
public abstract class TeachingActivity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Subject subject;
	
	@Enumerated(EnumType.STRING)
	private DayOfWeek day;
	
	private Integer startHour;
	
	private Integer durationInHours;
	
	private String roomId;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@LastModifiedBy
	private String lastModifiedBy;
	
	@ManyToMany
//	@OrderColumn(name="INDEX") + the collection must become List
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
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getDurationInHours() {
		return durationInHours;
	}

	public void setDurationInHours(Integer durationInHours) {
		this.durationInHours = durationInHours;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
