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

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");
        repo.save(new ErrorLog("Halo!"));// this is executed AFTER the method end.
        log.warn("Function End");
        teacherRepo.nativeInsert(null);
//        jdbcTemplate.update("INSERT INTO TEACHER(ID) VALUES (null)"); // because this throws exception,
        // the transaction started at line 26 is rolledback -> save :29 is lost.
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
//        log.debug("Halo2!");
//        System.out.println(teacherRepo.findAll());
    }
}
