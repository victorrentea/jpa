package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COURSE")
public class CourseActivity extends TeachingActivity {
	@ManyToOne
	@JoinColumn(name = "YEAR_ID")
	private StudentsYear year;

	public StudentsYear getYear() {
		return year;
	}

	public void setYear(StudentsYear year) {
		this.year = year;
	}
	
	
}
