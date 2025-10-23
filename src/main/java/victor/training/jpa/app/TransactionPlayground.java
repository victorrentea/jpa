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

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
  private final EntityManager entityManager;
  private final ServicePtMirela servicePtMirela;

  @Transactional // = Atomic=tot sau nimic
  public void firstTransaction() {
    log.debug("Function Begin");
    entityManager.persist(new ErrorLog("Halo!"));
    CompletableFuture.runAsync(() -> servicePtMirela.inTxCuMine())
        .exceptionally(ex -> {
          log.error("Exception in async call", ex);
          return null;
        });
    log.debug("Function End");
  }
}
@Service
@RequiredArgsConstructor
class ServicePtMirela{
  private final EntityManager entityManager;
  public void inTxCuMine() {
    entityManager.persist(new ErrorLog("Halo!"));
  }
}