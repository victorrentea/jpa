package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.repo.ErrorLogRepo;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.UncheckedIOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    public static final ThreadLocal<String> magie = new ThreadLocal<>();
    private final ErrorLogRepo repo;
    private final Other other;

    @Transactional
    public void firstTransaction()  {
        log.debug("Function Begin");
        repo.save(new ErrorLog("Inainte"));
        try {
            other.bizAdanc();
        } catch (Exception e) {
            other.saveError(e);
            log.error("SWallow: " + e);
        }
        repo.save(new ErrorLog("Dupa"));
        log.debug("Function End");
    }
    @Transactional
    public void secondTransaction() {
    }
}
@Slf4j
@RequiredArgsConstructor
@Service
class Other {
    private final ErrorLogRepo repo;

    @Transactional
    public void bizAdanc() {
        repo.save(new ErrorLog("BIZ mesaj"));
        if (true) {
            throw new UncheckedIOException(new IOException("Orice exceptie  (validate, ... conn timeout)"));
        }
    }

//    @Autowired
//    private Session session;
//    @Autowired
//    private EntityManager entityManager;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED) // NU MERGE CA NU TRECE PRIN PROXY!!
    // un apel de metoda in aceeasi clasa (local) NU TRECE PRIN PROXY.
    public void saveError(Exception e) {
        repo.save(new ErrorLog("EROARE VALEU: " + e.getMessage()));
    }





}
