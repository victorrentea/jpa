package victor.training.jpa.app.domain.entity;

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
public class StudentsGroup {
	private Long id;

	private String code;

	private StudentsYear year;

	private Set<LabActivity> labs = new HashSet<>();
	
	private List<String> emails = new ArrayList<>();

	public StudentsGroup() {
	}
	public StudentsGroup(String code) {
		this.code = code;
	}

}
