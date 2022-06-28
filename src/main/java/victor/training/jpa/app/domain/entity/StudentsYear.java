package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Getter
@Setter
public class StudentsYear {

	private Long id;
	
	private String code;
	
	private List<StudentsGroup> groups = new ArrayList<>();
	
	private Set<CourseActivity> courses = new HashSet<>();

	public StudentsYear() {
	}
	public StudentsYear(String code) {
		this.code = code;
	}

}
