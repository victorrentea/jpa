package victor.training.jpa.app.domain.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COURSE")
public class CourseActivity extends TeachingActivity {
//	private StudentsYear year;

//   @Column(nullable = false)
   private String siea;
}
