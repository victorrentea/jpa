package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

public class CourseActivity extends TeachingActivity {
	private StudentsYear year;

	public StudentsYear getYear() {
		return year;
	}

	public void setYear(StudentsYear year) {
		this.year = year;
	}
	
	
}
