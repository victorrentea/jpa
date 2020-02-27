package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final ErrorLogRepo errorLogRepo;

    private final Alta alta;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
        ErrorLog errorLog = errorLogRepo.findById(13L).get();
        ErrorLog dinAltQuery = alta.read();

        System.out.println(errorLog == dinAltQuery);
    }

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
        // THese share the same transaction:
        errorLogRepo.save(new ErrorLog("Fatala"));
        try {
            alta.altaMetoda();
        } catch (Exception e) {
            log.debug("Shawarma. Gogaltz. Inghit exceptii.");
        }
        log.debug("Cate sunt in baza acum pe tx mea : " + errorLogRepo.count());
//        errorLogRepo.flush(); // obliga Hibernate sa trimita in baza toate INSERT + UPDATE + DELETE pe care le avea de scris
//        log.debug("Se termina metoda");
    }
}
@Service
@RequiredArgsConstructor
class Alta {
    private final EntityManager em;
    private final ErrorLogRepo errorLogRepo;

    public void altaMetoda() {
        errorLogRepo.save(new ErrorLog("Fatala2"));
        throw new IllegalArgumentException("Bine Intentionata");
    }

    @Transactional
    public ErrorLog read() {
        ErrorLog errorLog = errorLogRepo.findById(13L).get();
        ErrorLog log = em.createQuery("SELECT e FROM ErrorLog e where e.id = 13", ErrorLog.class)
                .getSingleResult();
        return log;
    }
}