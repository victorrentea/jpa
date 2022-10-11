package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.ErrorLogBO;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.FileNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final DataSource dataSource;
    private Long firstId;

    @Transactional // stupid and dangerous as I only do 1 DB interaction
    public void secondTransaction_firstActually() {
        firstId = repo.save(new ErrorLog("Halo1!")).getId();
    }




    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value
    class SendEmailsAfterTxCommit {
        String stuffToDo;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(SendEmailsAfterTxCommit event) {
        log.debug("After the commit, whenver that happens: " + event.getStuffToDo());
    }

    @Autowired
    private Other other;

    @Transactional
    public void second() throws FileNotFoundException {
        log.debug("Function Begin");

        // repo
        ErrorLog firstRead = repo.findById(firstId).orElseThrow();
        firstRead.setMessage("different");
        // ???????repo.save(  dirty check + autoflush of changes.

//        ErrorLogBO bo = new ErrorLogBO(firstRead.getId(), firstRead.getMessage());
//
//        // domain
//        bo.setMessage("different");
//
//        // repo.save()
//        ErrorLog entityToSaveBack = new ErrorLog();
//        entityToSaveBack.setId(bo.getId());
//        entityToSaveBack.setMessage(bo.getMessage());
//        // triggers a .merge is preceded by anotehr (useless) SELECT
//        repo.save(entityToSaveBack);

        System.out.println("------");
//        System.out.println(repo.findById(firstId).get());
//        System.out.println(repo.findById(firstId).get());
//        System.out.println(repo.findById(firstId).get());
//        System.out.println(repo.findById(firstId).get());
//        System.out.println(repo.findById(firstId).get());
//        System.out.println(repo.findById(firstId).get());
        //
//
//r
//
//
//
//
//
//        repo.save(new ErrorLog("One"));
//        try {
//            other.secondMethod();
//        } catch (Exception e) {
//            other.saveError(e);
//        }
//        eventPublisher.publishEvent(new SendEmailsAfterTxCommit("Send kafka message, emails"));
//        log.debug("Function End");
    }


}

@Slf4j
@RequiredArgsConstructor
@Service
class Other {
    private ErrorLogRepo repo;

    @Transactional
    public ErrorLog secondMethod() {
        throw new RuntimeException("intentional");
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
        repo.save(new ErrorLog("BUBU: " + e.getMessage()));
    }
    // 1) whenever a @Transactional interceptor SEES an exception going out of its method,
    //      it KILLS the CURRENT (perhaps inherited) Transaction
    // 2)
}

//     @Transactional
//    fun transactionalMethodExample() {
//        log.info("Start transaction method")
//
//        val all = repository.findAll()
//        log.info("Read all data")
//
//        val toChange = changeEnum(all.first())
//        log.info("Changed data")
//
//        repository.save(toChange)
//        log.info("Saved data")
//
//        log.info("End transaction method")
//    }