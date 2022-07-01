package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter

@Entity
@DiscriminatorValue("LAB")
public class LabActivity extends TeachingActivity {

	@ManyToOne
	private StudentsGroup group;

}
