package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final DataSource dataSource;

    @Transactional(rollbackFor = Exception.class)
    public void firstTransaction() throws FileNotFoundException {
        log.debug("Function Begin");

        repo.save(new ErrorLog("Halo1!"));
        repo.save(new ErrorLog("Halo1!"));
        repo.flush(); // tells hibernate to WRITE immediately to DB (still in the current TX), not at the end of the Tx (default)

        log.debug("send kafka message, emails, some non-transacted side effect");
        log.debug("Function End - the inserts are FLUSHED to db right before the COMMIT = Write-Behind");
    }


    @Transactional // stupid and dangerous as I only do 1 DB interaction
    public void secondTransaction() {
        repo.save(new ErrorLog("Halo2!"));
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