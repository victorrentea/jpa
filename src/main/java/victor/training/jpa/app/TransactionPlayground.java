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
import java.util.concurrent.CompletableFuture;

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

        repo.save(new ErrorLog("One"));
        CompletableFuture.runAsync(() -> secondMethod())
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });

        eventPublisher.publishEvent(new SendEmailsAfterTxCommit("Send kafka message, emails"));
   }
    private ErrorLog secondMethod() {
        return repo.save(new ErrorLog(null));
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