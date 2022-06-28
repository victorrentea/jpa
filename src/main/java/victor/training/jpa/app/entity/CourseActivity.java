package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@DiscriminatorValue("COURSE")
public class CourseActivity extends TeachingActivity {
	@ManyToOne
	private StudentsYear year;

}
