package victor.training.jpa.app.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.ws.soap.MTOM;

@Entity
public class LabActivity extends TeachingActivity {

    //	private String a,b,c,d,e,f,g,h,j,k;
    @ManyToOne
    private StudentsGroup group;

    public StudentsGroup getGroup() {
        return group;
    }

    public void setGroup(StudentsGroup group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "LabActivity{" +
                "group=" + group +
                ", teachers=" + teachers +
                ", timeSlot=" + getTimeSlot() +
                '}';
    }
}
