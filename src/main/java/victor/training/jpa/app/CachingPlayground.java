package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.repo.SubjectRepo;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
class CachingPlaygroundGate {
   private final CachingPlayground playground;
   @PostConstruct
   public void launchPlay() {
      playground.play();
   }

}
@Component
@RequiredArgsConstructor
public class CachingPlayground {
   private final SubjectRepo subjectRepo;


   public void play() {

   }
}
