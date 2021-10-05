package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
@Getter
@Setter
public class TeacherDetails {
	
	private Long id;
	
	private String cv;

}
