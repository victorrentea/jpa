package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;
import victor.training.jpa.app.facade.dto.TimeSlotDto;

import java.time.DayOfWeek;
import java.util.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Teacher {
		@Id
	@GeneratedValue
	private Long id;

	private String name;

  public enum Grade {
    LECTURER("L"),
    PROFESSOR("P"),
    CONF("C"),
    ASSISTANT("A");

    public final String dbValue;
    Grade(String dbValue) {
      this.dbValue = dbValue;
    }
  }
  @Enumerated(EnumType.STRING)
	private Grade grade;
	
	@OneToOne(cascade = CascadeType.ALL)
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsConverter.class)
	private MoreTeacherDetails moreDetails;
	
	@ElementCollection
	@OrderColumn(name="INDEX") // 3 salveaza in DB ordinea manual setata de user in UI cu 🔼/🔽
//	@OrderBy("type ASC, value ASC") // 2 ti le sorteaza din query SQL trimis de JPA in DB
	private List<ContactChannel> channels = new ArrayList<>(); // 1 fara nimic, ordinea nu e garantata

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();
	
	@Enumerated(EnumType.STRING)
	private DayOfWeek counselingDay;

	private Integer counselingStartHour;

	private Integer counselingDurationInHours;

	private String counselingRoomId;

//	@Embedded
//	private TimeSlot counseling;

	public Teacher() {
	}

  public Set<Subject> getHeldSubjects() {
    return Collections.unmodifiableSet(heldSubjects);
  }

  public void addHeldSubject(Subject subject) {
    heldSubjects.add(subject);
    subject.setHolderTeacher(this);
  }

  public Teacher(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 '}';
	}
}
