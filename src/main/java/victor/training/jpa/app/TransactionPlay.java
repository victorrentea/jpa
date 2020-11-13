package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionPlay {
   private final EntityManager em;
   private final ErrorLogRepo repo;


   @PostConstruct
   public void init() {
       run();
   }
   @Transactional // doesn't work on @PostConstruct
   public void run() {
      em.persist(new ErrorLog("ONE"));
   }
}
