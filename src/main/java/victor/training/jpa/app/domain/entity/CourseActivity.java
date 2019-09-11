package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class CourseActivity extends TeachingActivity {

//	int a,b,c,d,e,f,g;

	@ManyToOne
	private StudentsYear year;

	public StudentsYear getYear() {
		return year;
	}

	public void setYear(StudentsYear year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "CourseActivity{" +
				"year=" + year +
				", teachers=" + teachers +
				", timeSlot=" + getTimeSlot() +
				'}';
	}
}
