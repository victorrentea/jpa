package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.ErrorTag;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.util.Set;

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

        ErrorTag tag = new ErrorTag("text");
        error.getTags().add(tag);
        em.persist(tag);

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM ERROR_LOG", Integer.class); // nu cauzeaza
        System.out.println(count);

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

//        em.flus()
//        repo.count() // SELECT COUNT(*)

        log.debug("Function End");
    }

//    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");
        ErrorLog errorLog = //repo.findById(1L).get();
                repo.queryCustomizat(1L);


        System.out.println("Imediat: " + errorLog.getIncaCeva());
        System.out.println("Imediat: " + errorLog.getMessage());

        Set<ErrorTag> setHackuit = errorLog.getTags();
        for (ErrorTag tag : setHackuit) {// face select
            System.out.println("Copil: " + tag );
        }
        for (ErrorTag tag : errorLog.getTags()) {
            System.out.println("Copil: " + tag );
        }

//        errorLog.setMessage("Uau! Auto-flushing Uite modelul asta de lucru te cupleaza intr-adevar la Hiberate");
    }
}
// changing an entitate "attached" to a Persistence COntext bound to the current thread
// is auto-flushed at the end of the current Tx