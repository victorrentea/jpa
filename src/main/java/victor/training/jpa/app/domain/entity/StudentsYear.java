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
@Entity
public class StudentsYear {

	@Id
	@GeneratedValue
	private Long id;
	
	private String code;
	
	@OneToMany(mappedBy = "year", orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderColumn(name = "POSITION")
	private List<StudentsGroup> groups = new ArrayList<>();
	
	@OneToMany(mappedBy = "year")
	private Set<CourseActivity> courses = new HashSet<>();

	public StudentsYear() {
	}
	public StudentsYear(String code) {
		this.code = code;
	}

}
