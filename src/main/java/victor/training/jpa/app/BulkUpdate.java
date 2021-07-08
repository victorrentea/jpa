package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Component
public class BulkUpdate {
   private final ErrorLogRepo repo;

   @Transactional
   public void persistInitData() {
      repo.save(new ErrorLog("INITIAL"));
   }
   
   @Transactional
   public void modify() {
      ErrorLog errorLog = repo.findAll().get(0);
      errorLog.getComments().size();
      errorLog.getTags().size();

      errorLog.setMessage("CHANGED");

      repo.bulkUpdate();

//      em.clear();

      System.out.println(errorLog);
      repo.save(errorLog);
   }
   private final EntityManager em;

}
