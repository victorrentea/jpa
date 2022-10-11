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
        repo.save(new ErrorLog("not null"));

        log.debug("Function End - the inserts are FLUSHED to db right before the COMMIT = Write-Behind");
        if (true) throw new FileNotFoundException("Raining in '95 when they created Java");
        repo.save(new ErrorLog("this too"));
    }

    @Transactional // stupid and dangerous as I only do 1 DB interaction
    public void secondTransaction() {
        repo.save(new ErrorLog("Halo2!"));
    }
}
