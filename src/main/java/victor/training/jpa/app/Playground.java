package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
        // THese share the same transaction:
        em.persist(new Teacher());
        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}
