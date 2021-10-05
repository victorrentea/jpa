package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionControlPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final AltService altService;
    @Autowired
    TransactionControlPlayground myself;

    @Transactional // deschide TX noua pentru ca inca nu exista una deschisa pe th curent
    public ErrorLog firstTransaction() {
        log.debug("Function Begin");
        ErrorLog e = new ErrorLog("Fara @Tr in jur");
        repo.save(e);
        try {
            altService.metodaDeBiz();
        } catch (Exception ex) {
            // ce exceptie?!
            ex.printStackTrace();
            altService.secondTransaction(ex.getMessage());
        }
//        em.flush();
        System.out.println(repo.count());// >> rezulta in SELECT * FROM ERROR_LOG >> il oblica pe Hib sa flush() la tot ce are
        log.debug("Function End");
        return e;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW) // NOT_SUPPORTED also works here
    public void secondTransaction(String message) {
        repo.save(new ErrorLog("Eroare: " + message));
        log.debug("Halo2!");
    }
//    @Autowired
//    private AltService altService;
}

//@Service
//@Retention(RetentionPolicy.RUNTIME)
//@Transactional
//@interface Facade {
//
//}
@Slf4j
@Component
@RequiredArgsConstructor
//@Facade
class AltService {
    private final ErrorLogRepo repo;

    @Transactional(propagation = Propagation.REQUIRES_NEW) // NOT_SUPPORTED also works here
    public void secondTransaction(String message) {
        repo.save(new ErrorLog("Eroare: " + message));
        log.debug("Halo2!");
    }
    @Transactional(rollbackFor = Exception.class) // prelua Tx deschisa la linia 28
    public void metodaDeBiz() throws IOException {
        repo.save(new ErrorLog("#sieu"));
//        throw new RuntimeException("BUBA");
        throw new IOException();

        // daca o exceptie "strabate" un proxy de @Transactional ==> tx curenta este distrusa
        // se marcheaza tx ca "rollback only"
    }
}
