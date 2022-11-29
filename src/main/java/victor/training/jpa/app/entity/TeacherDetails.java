package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class TeacherDetails {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Lob
	@Basic(fetch = FetchType.LAZY) // DOES NOT work anymore with spring boot
	private String cv;

}

