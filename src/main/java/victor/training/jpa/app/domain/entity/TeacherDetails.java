package victor.training.jpa.app.domain.entity;

import javax.persistence.*;


@Entity
class X {
	@Id
	private Long id;
}

@Entity
public class TeacherDetails {

	@Id 	@GeneratedValue
	private Long id;

	@ManyToOne
	X x;

	@Lob
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
