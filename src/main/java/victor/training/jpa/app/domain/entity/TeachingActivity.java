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
public abstract class TeachingActivity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Subject subject;
	
	private DayOfWeek day;
	
	private Integer startHour;
	
	private Integer durationInHours;
	
	private String roomId;
	
	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;
	
	private Set<Teacher> teachers = new HashSet<>();
	
}
