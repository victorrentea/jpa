package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
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
	

	
}
