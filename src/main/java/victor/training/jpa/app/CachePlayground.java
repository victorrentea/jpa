package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.CourseActivity;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

@Slf4j
@Component
@RequiredArgsConstructor
class CachePlaygroundLauncher {
   private final CachePlayground playground;

   @EventListener
   public void launchPlay(ContextRefreshedEvent event) {
      log.debug(">>>>>>>>>> Running Cache Playground code... <<<<<<<<<<<<");

      // 1st level cache is @Transactional-scoped
      // 2nd level cache for Subject
      // 2nd level cache for Subject.activities
      // 2nd level cache for Teacher

      Long subjectId = playground.insertSubject();

      playground.read(subjectId);
      playground.read(subjectId);

      log.debug("Subject cache size: " + CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("victor.training.jpa.app.domain.entity.Subject").getSize());
   }


}
@Slf4j
@Component
@RequiredArgsConstructor
public class CachePlayground {
   private final SubjectRepo subjectRepo;
   private final TeacherRepo teacherRepo;


   public Long insertSubject() {
      Subject subject = new Subject("Alghorithms");
      return subjectRepo.save(subject).getId();
   }

   @Transactional
   public void read(Long subjectId) {
      log.debug("Loading subject-----");
      Subject subject = subjectRepo.findById(subjectId).get();
      log.debug("Loaded subject: {}", subject);
      log.debug("Activities: " + subject.getActivities());
      log.debug("Done------");
   }
}
