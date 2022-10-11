package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
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

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");

        repo.save(new ErrorLog("Halo1!"));
        repo.save(new ErrorLog(null));

        log.debug("Function End - the inserts are FLUSHED to db right before the COMMIT = Write-Behind");
//        throw new IllegalArgumentException("Oups!");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
        System.out.println(teacherRepo.findAll());
    }
}
