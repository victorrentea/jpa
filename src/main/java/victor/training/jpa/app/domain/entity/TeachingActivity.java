package victor.training.jpa.app.domain.entity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // de ce nu MappedSuperclass ?
@DiscriminatorColumn(name = "DISCR")
public abstract class TeachingActivity {

	@Id
	@GeneratedValue
	private Long id;

//	private Subject subject;

//	private DayOfWeek day;

	private Integer startHour;

	private Integer durationInHours;

	private String roomId;

	private LocalDateTime lastModifiedDate;

	private String lastModifiedBy;

//	private Set<Teacher> teachers = new HashSet<>();


}
