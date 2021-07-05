package victor.training.jpa.app.domain.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("LAB")
public class LabActivity extends TeachingActivity {

//	private StudentsGroup group;

   private Integer sieu;

}
