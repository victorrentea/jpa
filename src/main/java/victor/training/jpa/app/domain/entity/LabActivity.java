package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LabActivity extends TeachingActivity {

	private String a,b,c,d,e,f,g,h,j,k;

	public void setA(String a) {
		this.a = a;
	}
	//	private StudentsGroup group;
//
//	public StudentsGroup getGroup() {
//		return group;
//	}
//
//	public void setGroup(StudentsGroup group) {
//		this.group = group;
//	}

}
