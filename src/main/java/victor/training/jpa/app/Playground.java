package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @Autowired
    private EntityManager em;
    private Long teacherId;


    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");

        TeacherDetails details = new TeacherDetails();
        details.setCv("pimped");
        em.persist(details);

        Teacher teacher = new Teacher();
        teacher.setName("Giumale");
        teacher.setDetails(details);
        System.out.println("Teacher.id inainte = " + teacher.getId());
        em.persist(teacher);
        System.out.println("Teacher.id dupa = " + teacher.getId());
        teacherId = teacher.getId();
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");

        Teacher teacher = em.find(Teacher.class, teacherId);
        teacher.setName("Cristian");
        System.out.println(teacher.getName());
        System.out.println(teacher.getDetails().getCv());
//        ActivitySearchCriteria criteria; // hm...
    }
}
