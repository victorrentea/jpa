package victor.training.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.domain.entity.TimeSlot;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

@Service
public class Curiozitati {

    @Autowired
    private EntityManager em;

    @Transactional
    public void cretz() {
        LabActivity lab = new LabActivity();
        lab.setId(1L);
        lab.setTimeSlot(new TimeSlot(DayOfWeek.MONDAY,6,2,"13"));
//        lab.
//        lab.setA("a hihihi");
        em.persist(lab);

        em.createQuery("SELECT t FROM TeachingActivity t", TeachingActivity.class).getResultList(); // UNIUNEA PROLETARILOR :P
    }
}
