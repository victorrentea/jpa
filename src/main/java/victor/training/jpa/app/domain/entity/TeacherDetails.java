package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

public class TeacherDetails {
	
	private Long id;
	
	private String cv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCv() {
		return cv;
	}

	public TeacherDetails setCv(String cv) {
		this.cv = cv;
		return this;
	}
	

	
}
