package victor.training.jpa.app.entity;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;


@Entity
public class Subject  {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	@NotNull
	@Size(min = 4, max= 20)
//	@Pattern(regexp = ".*")
//	@Email
	private String description;
	
	private boolean active;
	
	@ManyToOne
	private Teacher holderTeacher;
	
	@OneToMany(mappedBy="subject", cascade = ALL, orphanRemoval = true)
	private List<TeachingActivity> activities = new ArrayList<>();
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@LastModifiedBy
	private String lastModifiedBy;

	protected Subject() {
	}

	public Subject setName(String name) {
		this.name = name;
		return this;
	}

	public Subject(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	// setterul nu e necesar. Hibernate intra oricum cu reflection pe campuri private
//	public Subject setName(String name) {
//		this.name = Objects.requireNonNull(name); // validezi SI in setter
//		return this;
//	}

	public List<TeachingActivity> getActivities() {
		return Collections.unmodifiableList(activities);
	}

	public void removeActivity(long activityId) {
		TeachingActivity activity = activities.stream().filter(a -> a.getId().equals(activityId)).findFirst().get();

		activities.remove(activity);
		activity.setSubject(null);
	}
	public void addActivity(TeachingActivity activity) {
		activity.setSubject(this);
		activities.add(activity);
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public boolean isActive() {
		return this.active;
	}

	public Teacher getHolderTeacher() {
		return this.holderTeacher;
	}

	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public Subject setId(Long id) {
		this.id = id;
		return this;
	}



	public Subject setActive(boolean active) {
		this.active = active;
		return this;
	}

	public Subject setHolderTeacher(Teacher holderTeacher) {
		this.holderTeacher = holderTeacher;
		return this;
	}

	public Subject setActivities(List<TeachingActivity> activities) {
		this.activities = activities;
		return this;
	}

	public String toString() {
		return "Subject(id=" + this.getId() + ", name=" + this.getName() + ", active=" + this.isActive() + ", holderTeacher=" + this.getHolderTeacher() + ", activities=" + this.getActivities() + ", lastModifiedDate=" + this.getLastModifiedDate() + ", lastModifiedBy=" + this.getLastModifiedBy() + ")";
	}
}
