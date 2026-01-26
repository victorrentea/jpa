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

    public void firstTransaction() {
        log.debug("Function Begin");
        atomic(); // adnotarea @Transactional nu merge pe apel local (pe this.)
        log.info("Send ws:,API call... TeacherCreated");
    }

    @Transactional
    public void atomic() {
        repo.save(new Teacher().setName("John2"));
        repo.save(new Teacher().setName("Fratele".repeat(1000)));
    }
    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}
