package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("LAB")
public class LabActivity extends TeachingActivity {

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private StudentsGroup group;

	public StudentsGroup getGroup() {
		return group;
	}

	public void setGroup(StudentsGroup group) {
		this.group = group;
	}

}
