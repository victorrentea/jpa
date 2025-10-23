package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
  private final EntityManager entityManager;

  @Transactional // = Atomic=tot sau nimic
  public void firstTransaction() {
    log.debug("Function Begin");

    entityManager.persist(new ErrorLog("Halo!"));
    entityManager.flush(); // in ciuda INSERTULUI TRIMIS, se da rollback
    if (true) throw new RuntimeException("BUG🐞");
    entityManager.persist(new ErrorLog("Halo!"));

    log.debug("Function End");
  }
}
