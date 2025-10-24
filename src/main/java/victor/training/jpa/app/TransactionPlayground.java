package victor.training.jpa.app;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
  private final EntityManager entityManager;
  private final ServicePtMirela servicePtMirela;
  private final MeterRegistry meterRegistry;

  @Timed
  @Observed
  @Transactional // = Atomic=tot sau nimic
  public void firstTransaction()  {

    meterRegistry.timer("f*t_duration").record(()->anafu());
    meterRegistry.counter("oameni_omorati").increment(2.5);
    meterRegistry.gauge("teacher_count", entityManager,
        entityManager-> entityManager.createQuery
            ("SELECT COUNT(t) FROM Teacher t", Long.class)
            .getSingleResult());

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

  @SneakyThrows
  private String anafu() {
    Thread.sleep(1200);
    return "200 OK cu erori";
  }

  // @ de mai jos e ca si cum n-ar fi
  // LEGE: @Tranactional NU ARE EFECT CAND CHEMI METODA LOCAL (pe this)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @RolesAllowed("ROLE_ADMIN") // nu are efect chemata local => lasa-le doar pe @GET & co
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