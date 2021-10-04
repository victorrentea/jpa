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
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;

    @Transactional // @TransactionAttribute
    public void firstTransaction() {
        log.debug("Function Begin");

        ErrorLog error = new ErrorLog("Halo!");
        repo.save(error); // map.put(error.id, error);

        error.setMessage("Slim Shady");
        Long persistedId = error.getId();

        log.debug("ID nou: " + persistedId);

        ErrorLog error2 =
//            repo.findById(persistedId).get();
            repo.customFind(persistedId);
        // orice query pe care Hibe trebuie sa-l trimita in
        // DB ca SQL va fi mereu precedat de un AUTO-FLUSH al PersistenceContext
        log.debug("Sunt aceeeai instanta ? " + (error2 == error));
        ErrorLog error3 = repo.findById(persistedId).get();
        log.debug("Sunt aceeeai instanta ? " + (error3 == error2));

        log.debug("Function End");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
        ErrorLog errorLog = repo.findById(1L).get();
        errorLog.setMessage("Uau! Auto-flushing Uite modelul asta de lucru te cupleaza intr-adevar la Hiberate");
//        repo.save(errorLog);
    }
}
// changing an entitate "attached" to a Persistence COntext bound to the current thread
// is auto-flushed at the end of the current Tx