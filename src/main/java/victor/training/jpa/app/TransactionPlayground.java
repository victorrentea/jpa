package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.jpa.app.entity.ErrorLog;
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
    public void secondTransaction() {
        firstId = repo.save(new ErrorLog("Halo1!")).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void firstTransaction() throws FileNotFoundException {
        log.debug("Function Begin");

        repo.save(new ErrorLog("Second"));
        System.out.println("---");
        System.out.println(repo.findAll());
// error log 1 is found in the SELECT * FROM ERROR_LOG and because it's NOT in the 1st levle cache, Hibe goes and fetches the children for it.
        // Error log (second) is alread in cache -> no queries for it.

        eventPublisher.publishEvent(new SendEmailsAfterTxCommit("Send kafka message, emails"));

        log.debug("Problem#1: Confusing log message - why is this printed !??!");
        log.debug("Problem#2⚠️: Send kafka message, emails, some non-transacted side effect");
        log.debug("Problem#3: Fork behavior depending on the DB operation success");
        log.debug("Function End - the inserts are FLUSHED to db right before the COMMIT = Write-Behind");
    }

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value
    class SendEmailsAfterTxCommit{
        String stuffToDo;
    }

@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(SendEmailsAfterTxCommit event) {
    log.debug("After the commit, whenver that happens: " + event.getStuffToDo());
    }



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