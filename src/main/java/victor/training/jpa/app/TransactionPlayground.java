package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager em;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");

        repo.save(new ErrorLog("Halo!"));
        repo.save(new ErrorLog("Two"));

        log.debug("Function End");
        // Why are my inserts sent to DB after the method end ?
        // 1) to enable hibernate to batch your inserts together
        // 2) Faster if an ex happens before the tx end, then the INSERT was never even sent to DB over network
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
        System.out.println(teacherRepo.findAll());
    }
}
