package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TeachingActivity {

	@Id
	private Long id;

	@ManyToOne
	private Subject subject;

	@Embedded
	private TimeSlot timeSlot;

	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;

	@ManyToMany
	@JoinTable
	private Set<Teacher> teachers = new HashSet<>();
	
	}
