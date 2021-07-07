package victor.training.jpa.app.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("LAB")
public class LabActivity extends TeachingActivity {

   @ManyToOne
   @JoinColumn(name = "GROUP_ID")
	private StudentsGroup group;

   private Integer sieu;


}
