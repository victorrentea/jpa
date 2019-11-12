package victor.training.jpa.app;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeacherDetails;

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
        teacher.setName("Octavian Purdila");
        TeacherDetails details = new TeacherDetails();
        teacher.setDetails(details);

        em.persist(teacher);
//        em.persist(details);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");

        Teacher teacher = em.createQuery("FROM Teacher t WHERE t.name = ?", Teacher.class)
                .setParameter(0, "Octavian Purdila")
                .getResultList()
                .get(0);
        Subject subject = new Subject("SO2");



//        subject.setHolderTeacher(teacher);
        teacher.addSubject(subject);

        em.persist(subject);

        ErrorLog errorLog = em.find(ErrorLog.class, 1L);
        System.out.println(errorLog);
    }
}

