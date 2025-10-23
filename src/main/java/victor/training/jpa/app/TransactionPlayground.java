package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
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

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
  private final EntityManager entityManager;
  private final ServicePtMirela servicePtMirela;

  @Transactional // = Atomic=tot sau nimic
  public void firstTransaction()  {
    log.debug("Function Begin");
    entityManager.persist(new ErrorLog("Halo!"));
//    try {
//      metRea();
//    } catch (IOException e) {
//      throw new RuntimeException(e); // rollback ❤️
//    }
    // e ok dupa ce intrerupt functia sa cauzeze un COMMIT.
    // daca-i stii email-ul, si-mi-l = EJBullshit
    // > in anii 2005± aveau loc Razboaiele Exception system/business=asteptate=checked
    // => @Transactional + throws
    try {
      servicePtMirela.inTxCuMine();
    } catch (Exception e) { /*to-do*/ }
    log.debug("Function End");
  }

  private void metRea() throws IOException {
    if (true) throw new IOException("BUG🐞"); // CE BOU A GANDIT CA o ex checked
  }
}
@Service
@RequiredArgsConstructor
class ServicePtMirela{
  private final EntityManager entityManager;
  @Transactional(propagation = Propagation.REQUIRES_NEW)
//  @jakarta.transaction.Transactional()
  public void inTxCuMine() {
    entityManager.persist(new ErrorLog("Halo!"));
    if (true) throw new RuntimeException("BUG🐞");
  }
}