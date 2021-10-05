package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTANT
	}
	
	private Long id;

	private String name;
	
	private Grade grade;
	
	private TeacherDetails details;
	
	private List<ContactChannel> channels = new ArrayList<>();

	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	private Set<TeachingActivity> activities = new HashSet<>();
	
	private DayOfWeek counselingDay;

	private Integer counselingStartHour;

	private Integer counselingDurationInHours;

	private String counselingRoomId;

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

}
