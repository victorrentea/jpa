package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final DataSource ds; // o tempora, o mores....

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");
        ErrorLog entity = new ErrorLog("-Bani din cont");
        repo.save(entity);
        System.out.println("Intotdeauna dupa SAVE id = " + entity.getId());
        System.out.println("chiar DACA NU VEZI INSERTUL DUCANDU_SE IN BAZA!! Huh!?!");

        repo.save(new ErrorLog("null"));

        log.debug("Function End");
    }
//        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
    }
}
