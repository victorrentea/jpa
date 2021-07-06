package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static javax.persistence.FetchType.EAGER;

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

	@OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST)
	private List<TeachingActivity> activities = new ArrayList<>();
	
	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;

	public Subject() {}

	public Subject(String name) {
		this.name = name;
	}

	void setHolderTeacher(Teacher holderTeacher) {
		this.holderTeacher = holderTeacher;
	}

	public Subject addActivities(TeachingActivity ...activities) {
		Stream.of(activities).forEach(this::addActivity);
		return this;
	}
	public void addActivity(TeachingActivity activity) {
		activities.add(activity);
		activity.setSubject(this);
	}

	public List<TeachingActivity> getActivities() {
		return unmodifiableList(activities);
	}
}
