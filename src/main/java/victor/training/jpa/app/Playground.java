package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service // @Stateless
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
//    @TransactionAttribute
    public void firstTransaction() {
        log.debug("Halo!");
        ErrorLog errorLog = new ErrorLog("buba");
        em.persist(errorLog);
        System.out.println("Am inserat un rand cu ID-ul " + errorLog.getId());

        ErrorLog errorLog1 = new ErrorLog("alta");
        em.persist(errorLog1);

        Teacher teacher = new Teacher();
        TeacherDetails details = new TeacherDetails();
        teacher.setDetails(details);

        em.persist(teacher);
        teacher.setName("Octavian Purdila");
        em.persist(teacher);

        teacher.setName("Octavian Purdila2");
        teacher.getChannels().add(new ContactChannel(ContactChannel.Type.FACEBOOK, "Krueger"));
        teacher.getChannels().add(new ContactChannel(ContactChannel.Type.PERSONAL_EMAIL, "cosmar@gmail.com"));
//        em.persist(details);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");

        Teacher teacher = em.find(Teacher.class, 3L);
        Subject subject = new Subject("SO2");
        teacher.addSubject(subject);
        em.persist(subject);
        LabActivity labActivity = new LabActivity();
        teacher.getActivities().add(labActivity);
        labActivity.getTeachers().add(teacher);
        em.persist(labActivity);

        System.out.println("--- linie ----");
        System.out.println("oare ce fel de lista e asta ?!!? "  +teacher.getChannels().getClass());
        for (ContactChannel channel : teacher.getChannels()) {
            System.out.println(channel);
        }
        for (ContactChannel channel : teacher.getChannels()) {
            System.out.println(channel);
        }

        ErrorLog errorLog = em.find(ErrorLog.class, 1L);
        System.out.println(errorLog);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void jsp(Teacher teacher) {
        for (ContactChannel channel : teacher.getChannels()) {
            System.out.println(channel);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void thirdTransaction() {

        CourseActivity course = new CourseActivity();

//        Subject subject = em.find(Subject.class, 5L);
        // chestia de mai sus produce urm query (selectand din 3 tabele):
        // select subject0_.id as id1_4_0_, subject0_.active as active2_4_0_, subject0_.teacher_id as teacher_6_4_0_, subject0_.last_modified_by as last_mod3_4_0_, subject0_.last_modified_date as last_mod4_4_0_, subject0_.name as name5_4_0_, teacher1_.id as id1_5_1_, teacher1_.details_id as details_8_5_1_, teacher1_.grade as grade2_5_1_, teacher1_.name as name3_5_1_, teacher1_.counseling_day as counseli4_5_1_, teacher1_.duration_in_hours as duration5_5_1_, teacher1_.room_id as room_id6_5_1_, teacher1_.start_hour as start_ho7_5_1_, teacherdet2_.id as id1_7_2_, teacherdet2_.cv as cv2_7_2_ from subject subject0_ left outer join teacher teacher1_ on subject0_.teacher_id=teacher1_.id left outer join teacher_details teacherdet2_ on teacher1_.details_id=teacherdet2_.id where subject0_.id=?

        Subject subject = em.getReference(Subject.class, 5L); // eviti selectul de mai sus get
        System.out.println("Oare ce mama masii e subjectu asta: " + subject.getClass());
        course.setSubject(subject);
        em.persist(course);
        em.persist(new Teacher("Sf. Giumale"));
    }

    @Transactional
    public void day2_take1() throws Exception {

        System.out.println("Oare ce implementare primesc de EntityManager ? " + em.getClass());
        Teacher teacher = em.find(Teacher.class, 3L);
        System.out.println(teacher.getName());
        Subject subject = em.find(Subject.class, 5L);
        System.out.println( subject.getHolderTeacher() == teacher);
        Teacher teacher3 = em.createQuery(
                "SELECT t from Teacher t where t.name='Octavian Purdila2'",
                Teacher.class).getSingleResult();
        teacher.setName("Oups! Tranzactia s-a comis.");
        System.out.println(teacher3 == teacher);

        System.out.println("S-a intamplat ceva?");
//        if (true) throw new Exception();

        try {
            altu.altaMetoda(teacher);
            //B
        }catch (Exception e) {
            // gogaltz
        }
        System.out.println("Eu am cerut. Cere si ti se va da. Dar oare ce am primit? " + altu.getClass());

    }
    @Autowired
    AltEJB altu;


//    = new AltEJB() {
//        @Override
//        public void altaMetoda(Teacher teacher) throws Exception {
//            tx.start()
//            super.altaMetoda(teacher);
//            tx.commit;
//        }catch () {tx.rollback
//        }
//    };
    @Transactional(propagation = Propagation.REQUIRED)
    public void day2_take2(Teacher teacher) {
        teacher.setName("Altu"); // detasata deja cand e data param
        Teacher teacher2 = altu.incarcaTeacher();
        teacher2.setName("Altu2"); // detasata la finalul metodei apelate

        Teacher teacher1 = em.find(Teacher.class, 3L);
        System.out.println(teacher == teacher1);
        System.out.println(teacher == teacher2);
        System.out.println(teacher1 == teacher2);
    }
    @Transactional
    public void day2_take3_lucian() {
        Teacher t = em.find(Teacher.class, 3L);
        t.setName("Rughinish");
        t.setDetails(null); // + orphan removal = dispare copilu
        altu.cautalPePurdila();
    }
}
@Service
class AltEJB {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public void altaMetoda(Teacher teacher) throws Exception {
        Teacher teacher2 = em.find(Teacher.class, 3L);
        System.out.println(teacher2.getName());
        System.out.println(teacher == teacher2);
        new RuntimeException().printStackTrace();
        throw new RuntimeException();
//A
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Teacher incarcaTeacher() {
        return em.find(Teacher.class, 3L);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cautalPePurdila() {
        new RuntimeException("pt proxy, sus halba").printStackTrace();
        Long n = em.createQuery("SELECT count(*) FROM Teacher t WHERE t.name = :name", Long.class)
                .setParameter("name", "Octavian Purdila2")
                .getSingleResult();
        System.out.println("L-am gasit ? : " + n);
    }
}

