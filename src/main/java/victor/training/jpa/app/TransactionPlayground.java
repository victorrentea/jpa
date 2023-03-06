package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo errorLogRepo;
    private final OtherClass otherClass;

    @Transactional(/*timeout = 5*/
//    isolation = Isolation.READ_UNCOMMITTED // end of the world-> use Mongo instead!
    ) // this proxy does NOT see any exception (it gets swallowed)
    public void firstTransaction() {
        log.debug("Function Begin talking to a proxied reference: " + otherClass.getClass());
        errorLogRepo.save(new ErrorLog("Halo!") );// this is executed AFTER the method end.
        try {
            otherClass.otherMethod();
        } catch (Exception e) {
            // swallow
            otherClass.secondTransaction(e);
            // equivalent, non AOP (FP-like) - not recommended at scale
//            TransactionTemplate transactionTemplate; // configure to
//            transactionTemplate.setPropagationBehaviorName("REQUIRES_NEW");
//            transactionTemplate.executeWithoutResult(s ->
//                    errorLogRepo.save(new ErrorLog("Error: " + e.getMessage()));
//            );
        }
        errorLogRepo.saveAndFlush(new ErrorLog("You will see me in the logs as INSERT, but I will never be commited."));
        log.warn("Function End");
    }



    @Transactional
    public void jpa1() {
        ErrorLog e = new ErrorLog("ONE");
        System.out.println("Entity before: " + e);
        errorLogRepo.save(e);
        System.out.println("Entity after: " + e);
        errorLogRepo.save(new ErrorLog("TWO")); // hashCode equals on @Entity, why @Data is a bad idea on @Entity

        // any JQPL or native query hibernate has to run on the DB it will first flush the
        // write-buffer (changes pending in the Persistence Context) in order to allow the DB
        // to return you correct results.; any native query would also 'flush the persistence context' changes
//        System.out.println(errorLogRepo.count());
        System.out.println(teacherRepo.count());

        teacherRepo.flush();// flushes all changes in the persistence context forcefully
        // when would you ever need that?
        // 1. if you are about to call a PL/SQL procedure in the current transaction,
        // hibernate might need to be told to manually flush the changes on the current CONNTEXT;
        // 2. if you are NOT using hibernate to go to the database (eg. JdbcTemplate, MyBatis, jooq)
            // => recommended to only use SPring data Jpa repos (@Query(native=true) vs plain JdbcTemplate usage)

        log.info("Method exit");
    }
    @Transactional
    public void jpa2() {
        ErrorLog errorLog = errorLogRepo.findById(1L).orElseThrow();

        errorLog.setMessage("Dirty Changed Entity in a @Transactional method gets flushed update to DB at the end of the tx");

        System.out.println(errorLogRepo.count()); // forced the UPDATE to run before the query
        System.out.println("EXIT METHOD--");

        ErrorLog entity = errorLogRepo.findById(1L).orElseThrow();
        System.out.println("are the two entities == ? " + (errorLog == entity));
        System.out.println("I got the entity (supposedly), but surprise: lazy loading later when @Data tostring " +
                           "prints the children collections");
        System.out.println("Let me find id=1 again in the same tx. " +
           "Would I see a SELECT for this ? " + entity);
    }




//    @Transactional
//    public void badForPerformance() {
////        new RestTemplate().getForObject() from another API-> why are we doing REST stuff in a @Transaction?!! =>
//                    // if the call takes 2 sec => you just paralyzed 1 connection from the pool (max=20) for 2 sec
//                // you took 5% of your system for 2 sec. if 20 such calls  to badForPerformance arrive,
//        // your system is unresponsive for 2 sec.
//        // WSDL  call
////        WebClinet..
//        // in grafana you will see a large connection acquisition time
//
//        errorLogRepo.save(new ErrorLog("Stuff1"));
//        errorLogRepo.save(new ErrorLog("Stuff2"));
//
//    }
}
@RequiredArgsConstructor
@Component
class OtherClass {
    private final ErrorLogRepo errorLogRepo;
    private final TeacherRepo teacherRepo;
    @Transactional/*(rollbackFor = Exception.class)*/ // but this one DOES -> it marks the transaction for roolback only =true (aka ZOMBIE transaction)
    public void otherMethod() throws IOException { // behold Java, the only language in the world where you need to declare every checked exception you throw.
        teacherRepo.nativeInsert(1L); // this was inserted in the DB alone
        teacherRepo.nativeInsert(2L); // failed
        throw new IOException("Oups!");
    }
//    @TransactionAttribute(REQUIRES_NEW) // ejb . in EJB in 2005 TechnicalException < > BusinessException;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction(Exception e) {
        errorLogRepo.save(new ErrorLog("Error: " + e.getMessage()));
    }
}

