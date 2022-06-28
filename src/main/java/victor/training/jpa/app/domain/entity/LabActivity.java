package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class LabActivity extends TeachingActivity {
	private StudentsGroup group;
}
