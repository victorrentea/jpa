package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Getter
@Setter

@Entity
@DiscriminatorValue("LAB")
public class LabActivity extends TeachingActivity {

	@ManyToOne
	private StudentsGroup group;

}
