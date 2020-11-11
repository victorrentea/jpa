package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.repo.SubjectRepo;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
class CachePlaygroundGate {
   private final SubjectRepo subjectRepo;
   private final CachePlayground playground;
   @EventListener
   public void launchPlay(ContextRefreshedEvent event) {
      log.debug(">>>>>>>>>> Running Cache Playground code... <<<<<<<<<<<<");

      Long subjectId = subjectRepo.save(new Subject("Alghorithms")).getId();

      playground.play(subjectId);
   }

}
@Slf4j
@Component
@RequiredArgsConstructor
public class CachePlayground {
   private final SubjectRepo subjectRepo;

   public void play(Long subjectId) {
      Subject subject = subjectRepo.findById(subjectId).get();
      log.debug("Loaded subject: {}", subject);
   }
}
