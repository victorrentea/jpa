package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.TeacherDetails;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.sql.Connection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;

    @Transactional // tells spring to create a proxy(subclass) for this class and inject it anywhere it's @Autowired
    public void firstTransaction() {
//        connection.setAutoCommit(false); // start a tx then later .commit / rollback
        log.debug("Function Begin");
        repo.save(new ErrorLog("Halo!") );// this is executed AFTER the method end.
        otherMethod();
        log.warn("Function End");
        // the transaction started at line 26 is rolledback -> save :29 is lost.
    }

    private void otherMethod() {
        // runs in the SAME tx started at :25 (just as save:29)
        teacherRepo.nativeInsert(1L);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
//        log.debug("Halo2!");
//        System.out.println(teacherRepo.findAll());
    }
}
