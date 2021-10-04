package victor.training.jpa.app.domain.entity;

import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.DayOfWeek;
import java.util.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
//@EqualsAndHashCode
public class Teacher {
	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTANT
	}

	@Id
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@OneToOne
	@JoinColumn(name = "DETAILS_ID")
	private TeacherDetails details;

//	@OneToMany
//	@JoinColumn(name="TEACHER_ID")
	@ElementCollection
	@CollectionTable(name = "TEACHER_CONTACT_CHANNELS")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;


	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();

	@Embedded
	private TimeSlot counseling;

	private Teacher() {
	}

	public List<ContactChannel> getChannels() {
		return Collections.unmodifiableList(channels);
	}

	public Teacher(String name) {
		this.name = Objects.requireNonNull(name);
	}

}
