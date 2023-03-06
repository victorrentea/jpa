package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.runtime.CFlow;
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
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;

//    @Transactional // tells spring to create a proxy(subclass) for this class and inject it anywhere it's @Autowired
    public void firstTransaction() {
        log.debug("Function Begin");
        repo.save(new ErrorLog("Halo!") );// this is executed AFTER the method end.
        otherMethod(); // when calling HERE the otherMethod, there is NO SPring proxy intercepting the call < because it's a local method call..
        log.warn("Function End");
    }
    @Transactional
    public void otherMethod() {
        teacherRepo.nativeInsert(1L); // this was inserted in the DB alone
        teacherRepo.nativeInsert(null); // failed
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
//        log.debug("Halo2!");
//        System.out.println(teacherRepo.findAll());
    }
}
