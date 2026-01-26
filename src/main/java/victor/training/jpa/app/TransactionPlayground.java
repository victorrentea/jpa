package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.TeacherRepo;

import jakarta.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final TeacherRepo repo;
    private final AltService altService;

    public void firstTransaction() {
        log.debug("Function Begin");
        altService.atomic(); // adnotarea @Transactional nu merge pe apel local (pe this.)
        log.info("Send ws:,API call... TeacherCreated");
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}

@Slf4j
@RequiredArgsConstructor
@Service
class AltService {
    private final TeacherRepo repo;
    @Transactional
    public void atomic() {
        repo.save(new Teacher().setName("John2"));
        repo.save(new Teacher().setName("Fratele".repeat(1000)));
    }
}