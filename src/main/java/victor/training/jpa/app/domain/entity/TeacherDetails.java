package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class TeacherDetails  extends AbstractEntity  {

	@Lob
	private String cv;

	public String getCv() {
		return cv;
	}

	public TeacherDetails setCv(String cv) {
		this.cv = cv;
		return this;
	}
	

	
}
