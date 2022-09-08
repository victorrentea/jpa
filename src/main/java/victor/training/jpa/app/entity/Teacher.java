package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import victor.training.jpa.app.entity.converter.GradeConverter;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsAsJSONConverter;

import java.sql.Clob;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Teacher {

	public enum Grade {
		LECTURER("L"),
		PROFESSOR("P"),
		CONF("C"),
//		LECTURERA("L"),
		ASSISTANT("A");

		public final String dbValue;
		Grade(String dbValue) {
			this.dbValue = dbValue;
		}

		public String getDbValue() {
			return dbValue;
		}

		static {
			if (Stream.of(values()).map(Grade::getDbValue).collect(Collectors.toSet()).size() != values().length) {
				throw new IllegalArgumentException("BUM> la deploy.");
			}
		}
	}
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Lob
	private String cvDocxXml; // CLOB
//	private Clob cvDocxXml;//TODO upload 1gb de pe file systems direct in DB fara sa-l tii in memorie

	@Enumerated(EnumType.STRING)
	@Convert(converter = GradeConverter.class)
	private Grade grade;
	
	// fetch=LAZY or invert the link to retrieve details by teacher via repo
	@OneToOne(
		)
	private TeacherDetails details;

	@Convert(converter = MoreTeacherDetailsAsJSONConverter.class)
	private MoreTeacherDetails moreDetails;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

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
