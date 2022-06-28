package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class TeacherDetails {
	private Long id;
	
	private String cv;

}
