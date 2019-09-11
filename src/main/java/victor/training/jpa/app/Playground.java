package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;

import javax.persistence.EntityManager;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @Autowired
    private EntityManager em;
    private Long teacherId;


    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");

//        Teacher tdejaAcolo = new Teacher();
//        tdejaAcolo.setId(2L);
//        em.merge(tdejaAcolo);

        TeacherDetails details = new TeacherDetails();
        details.setCv("pimped");
//        em.persist(details); // putem sa-l scoatem pe asta ?

        Teacher teacher = new Teacher();
        teacher.setName("Giumale");
        teacher.setDetails(details);
        System.out.println("Teacher.id inainte = " + teacher.getId());
        em.persist(teacher);
        System.out.println("Teacher.id dupa = " + teacher.getId());
        teacherId = teacher.getId();


        //        em.persist(subject); // discutabil, deoarece viata cursului de FP nu depinde de existenta lui Giumale.
        teacher.addSubject(new Subject("ML"));
        teacher.addSubject(new Subject("FP"));
        teacher.addSubject(new Subject("MAS"));

        teacher.addActivity(new LabActivity());
        teacher.addActivity(new LabActivity());

        // teacher.getHeldSubjects().add(subject) // crapa la runtime, fraere
        // subject.setHolderTeacher(teacher); nu compileaza, fraere
        // Unde, fraere = tu peste 6 luni
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");

        Teacher teacher = em.find(Teacher.class, teacherId);
        teacher.setName("Cristian");
        System.out.println(teacher.getName());
        System.out.println(teacher.getDetails().getCv());

        System.out.println(teacher.getHeldSubjects());
//        ActivitySearchCriteria criteria; // hm...
    }
}
