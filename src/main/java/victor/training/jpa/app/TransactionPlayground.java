package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.sql.Connection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager em;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final OtherService other;

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");

        repo.save(new ErrorLog("before biz logic!"));

        try {
            bizLogic();
        } catch (Exception e) {
            other.persistErrorInDB(e.getMessage());
            throw e;
        }
        repo.flush(); // manual flushing: use in tests, avoid in prod

        System.out.println("How many rows are in db?"  + repo.count()); // if hibernate has to RUN and SELECT in DB while there are changes to flush to db,
        // it will first auto-flush them before the select. WHY?
        // If it didn't do that, the results of the query would LIE to you. you would not see what you (thought) you inserted before

        // autoflushing
        // != commit (the data is not yet commited)
//        if (true) throw new RuntimeException("Rollback of data that was INSERTED in the current connection");

        log.debug("Function End");
        // Why are my inserts sent to DB after the method end ? = write behind
        // 1) to enable hibernate to batch your inserts together
        // 2) Faster if an ex happens before the tx end, then the INSERT was never even sent to DB over network

    }

    private void bizLogic() {
        //bizLogic
        repo.save(new ErrorLog("from biz logic!"));
    }


    @Transactional
    public void secondTransaction() {
        ErrorLog errorLog = repo.findById(1L).orElseThrow();
        em.detach(errorLog); // tell hibernate to forget about that entity : not monitor and remove it from 1st level cache
        errorLog.setMessage("dirty entities are auto-flushed at the end of tX");
//        repo.save(errorLog);
        // Arch decision: do we want this feature or not ?
    }
}

@Slf4j
@RequiredArgsConstructor
@Service
class OtherService {
    @Autowired
    private ErrorLogRepo repo;

    public void method() {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistErrorInDB(String message) {
        log.debug("On what thread am I here ?");
        // transaction opened on :24 propagates here magically via the thread.?!?!? huh?
        repo.save(new ErrorLog("ERROR:" + message));
    }
}