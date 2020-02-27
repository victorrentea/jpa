package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final ErrorLogRepo errorLogRepo;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
        // THese share the same transaction:
        errorLogRepo.save(new ErrorLog("Fatala"));
        errorLogRepo.save(new ErrorLog("Fatala2"));
        log.debug("Cate sunt in baza acum pe tx mea : " + errorLogRepo.count());
//        errorLogRepo.flush(); // obliga Hibernate sa trimita in baza toate INSERT + UPDATE + DELETE pe care le avea de scris
        throw new IllegalArgumentException("Bine Intentionata");
//        log.debug("Se termina metoda");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}
