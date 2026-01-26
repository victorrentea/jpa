package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import jakarta.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final TeacherRepo repo;

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");
        repo.saveAndFlush(new Teacher().setName("John".repeat(1000)));
        log.info("Send ws:,API call... TeacherCreated");
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}
