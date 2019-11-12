package victor.training.jpa.app;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Service
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
    }
}

