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

        Teacher tavi = new Teacher();
        tavi.setName("Octavian Purdila");
        TeacherDetails teacherDetails = new TeacherDetails();
        tavi.setDetails(teacherDetails);

        em.persist(teacherDetails);
        em.persist(tavi);
        alta();
    }

    private void alta() {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");

        ErrorLog errorLog = em.find(ErrorLog.class, 1L);
        System.out.println(errorLog);
    }
}

