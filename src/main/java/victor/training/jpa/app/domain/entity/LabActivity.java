package victor.training.jpa.app.domain.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("LAB")
public class LabActivity extends TeachingActivity {

   @ManyToOne
   @JoinColumn(name = "GROUP_ID")
	private StudentsGroup group;

   private Integer sieu;


}
