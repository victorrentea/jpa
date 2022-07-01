package victor.training.jpa.app.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "DISCR")
public abstract class TeachingActivity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Subject subject;
	
	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfWeek;
	
	private Integer startHour;
	
	private Integer durationInHours;
	
	private String roomId;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@LastModifiedBy
	private String lastModifiedBy;
	
	@ManyToMany // OWNER SIDE.❤️ YOU HAVE TO SET THIS

	@Setter(AccessLevel.NONE)
//	@OrderColumn(name="INDEX") + the collection must become List
	private Set<Teacher> teachers = new HashSet<>();


	public Set<Teacher> getTeachers() {
		return Collections.unmodifiableSet(teachers);
	}

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
		teacher.activities.add(this);
	}
}
