package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TeacherDetails {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String cv;

}
