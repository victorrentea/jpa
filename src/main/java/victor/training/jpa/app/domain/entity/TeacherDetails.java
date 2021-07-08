package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Clob;

@Entity
public class TeacherDetails {

	@Id
	@GeneratedValue
	private Long id;

	@Lob
	private Clob cv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Clob getCv() {
		return cv;
	}

	public TeacherDetails setCv(Clob cv) {
		this.cv = cv;
		return this;
	}
	

	
}
