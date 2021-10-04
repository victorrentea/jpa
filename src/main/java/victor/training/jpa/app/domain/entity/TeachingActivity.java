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
//import victor.training.jpa.perf.Country;

//// SINGLE TABLE
//class Vessel  extends Record{
// NOT NULL flag
// }
//class Person  extends Record{}
//class Company extends Record{}


// @Entity INh (TABLE_PER_CLASS)
//@MappedSuperclass
//abstract class Label {
//	@Id
//	Long id;
//	String code;
//	String label;
//}
//@Entity
//class Country extends Label {
//}
//@Entity
//class Region extends Label {}
//class Currency extends Label {}
//class PEPTrackingType extends Label {}



@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class TeachingActivity {
	
	@Id
	@GeneratedValue
	private Long id;

	@Transient
	private Subject subject;

	@Embedded
	private TimeSlot timeSlot;

	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;

	@ManyToMany
	@JoinTable
	private Set<Teacher> teachers = new HashSet<>();
	
}
