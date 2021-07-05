package victor.training.jpa.app.domain.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("COURSE")
public class CourseActivity extends TeachingActivity {
   @ManyToOne
   @JoinColumn(name = "YEAR_ID")
	private StudentsYear year;

//   @Column(nullable = false)
   private String siea;
}
