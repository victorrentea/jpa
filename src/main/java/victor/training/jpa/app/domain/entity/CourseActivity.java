package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
public class CourseActivity extends TeachingActivity {
	@Transient
	private StudentsYear year;

}
