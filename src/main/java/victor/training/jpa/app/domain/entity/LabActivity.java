package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LabActivity extends TeachingActivity {

//	private StudentsGroup group;

   private int sieu;

}
