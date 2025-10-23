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
    entityManager.persist(new ErrorLog("Halo1!"));
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
      this.inTxCuMine();
    } catch (Exception e) { /*to-do*/
    e.printStackTrace();}
    log.debug("Function End");
  }
  // @ de mai jos e ca si cum n-ar fi
  // LEGE: @Tranactional NU ARE EFECT CAND CHEMI METODA LOCAL (pe this)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void inTxCuMine() {
    entityManager.persist(new ErrorLog("Halo2!"));
    if (true) throw new RuntimeException("BUG🐞");
  }
}
@Service
@RequiredArgsConstructor
class ServicePtMirela{
  private final EntityManager entityManager;
}