package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
//    private final TeacherRepo teacherRepo;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");

        Teacher t = new Teacher();
        Subject s = new Subject();

        // TREBUIE SA SE INTAMPLE MEREU IMPREUNA! Temporal Coupling

        t.addSubject(s);

        // t.getHeldSubjects().add(s); // exception
//        s.setTeacher(t); // nu compileaza

        em.persist(t);
        em.persist(s);



        // THese share the same transaction:
//        em.persist(new Teacher());
//        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
//        System.out.println(teacherRepo.findAll());
    }
}
