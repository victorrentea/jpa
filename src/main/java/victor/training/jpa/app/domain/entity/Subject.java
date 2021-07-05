package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Subject { // SOLUTION
	@Id
	@GeneratedValue
	@Getter
	private Long id;

//	@Getter
//	@Setter
	private String name;
	
	private boolean active;

	@ManyToOne
	@JoinColumn(nullable = false, name = "TEACHER_ID") // OWNER SIDE
	private Teacher holderTeacher;

//	private List<TeachingActivity> activities = new ArrayList<>();
	
	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;


	void setHolderTeacher(Teacher holderTeacher) {
		this.holderTeacher = holderTeacher;
	}

//
//
//	public Subject() {
//	}
//
//
//	public boolean isActive() {
//		return active;
//	}
//
//
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//
//
//	public Subject(String name) {
//		this.name = name;
//	}
//
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
//	public Teacher getHolderTeacher() {
//		return holderTeacher;
//	}
//
//	public Subject setHolderTeacher(Teacher holder) {
//		this.holderTeacher = holder;
//		return this;
//	}
//
//	public List<TeachingActivity> getActivities() {
//		return activities;
//	}
//
//	public void setActivities(List<TeachingActivity> activities) {
//		this.activities = activities;
//	}
//
//	public String getLastModifiedBy() {
//		return lastModifiedBy;
//	}
//
//	public LocalDateTime getLastModifiedDate() {
//		return lastModifiedDate;
//	}
//
//	public void setLastModifiedBy(String lastModifiedBy) {
//		this.lastModifiedBy = lastModifiedBy;
//	}
//
//	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
//		this.lastModifiedDate = lastModifiedDate;
//	}
	
}
