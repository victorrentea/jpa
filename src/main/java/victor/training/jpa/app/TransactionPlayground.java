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
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.domain.entity.ContactChannel.Type;
import victor.training.jpa.app.repo.SubjectRepo;

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
    private final SubjectRepo subjectRepo;
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

        em.persist(new CourseActivity());
        em.persist(new LabActivity());
        t.getChannels().add(new ContactChannel(Type.FACEBOOK, "contu_profu"));
        t.getChannels().add(new ContactChannel(Type.PERSONAL_PHONE, "8989989"));
        t.getChannels().add(new ContactChannel(Type.PERSONAL_EMAIL, "ptSubscribeLa....."));


        System.out.println(subjectRepo.searchByCeva());


        // THese share the same transaction:
//        em.persist(new Teacher());
//        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
        em.find(Teacher.class, 1L).getChannels().remove(0);
//        System.out.println(teacherRepo.findAll());
    }
}
