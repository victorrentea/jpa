package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@Entity
@DiscriminatorValue("COURSE")
public class CourseActivity extends TeachingActivity {
	@ManyToOne
	private StudentsYear year;

}
