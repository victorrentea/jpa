package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Getter
@Setter

@Entity
public class StudentsGroup {
	@Id
	@GeneratedValue
	private Long id;

	private String code;

	@ManyToOne
	private StudentsYear year;

	@OneToMany(mappedBy = "group")
	private Set<LabActivity> labs = new HashSet<>();
	
	@ElementCollection
	private List<String> emails = new ArrayList<>();

	public StudentsGroup() {
	}
	public StudentsGroup(String code) {
		this.code = code;
	}

}
