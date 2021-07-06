package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class PropagationAndExceptions {
   private final ErrorLogRepo repo;
   private final AltaClasa alta;
   public void first() {
//      restcall 1 sec
      try {
         logica();
      } catch (Exception e) {
         alta.saveError(e);
         log.error("Buba"+e);
      }
      repo.save(new ErrorLog("Mesaj de Biz"));
      repo.flush();
   }

   @Transactional
   private void logica() throws IOException {
      ErrorLog ee = new ErrorLog("Mesaj de Biz");
      repo.save(ee);
      log.debug("ID :  " + ee.getId());
      alta.metodaDeLogica();
   }

}
@Component
@RequiredArgsConstructor
class AltaClasa {
   private final ErrorLogRepo repo;
   @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class) // asta e pusa DEGEABA AICI
   public void metodaDeLogica() throws IOException {
//      throw new IOException(); // nu face rollback !! PANICA !
      System.out.println(repo.findAll());
      throw new NullPointerException(); // rollback doar pe Runtime!
   }
   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void saveError(Exception e) {
      repo.save(new ErrorLog("Eroare: " + e.getMessage()));
   }
}
