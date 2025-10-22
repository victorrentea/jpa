package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.TeacherDetails;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaPlayground {
   private final EntityManager em;

   @Transactional // in loc de @TransactionAttribute
   public void play() throws Exception {
     Teacher teacher = new Teacher()
         .setName("Newton")
         .setDetails(new TeacherDetails()
             .setCv("pimped"));
     Subject subject = new Subject();
//     teacher.getHeldSubjects().add(subject); //ERROR
//     subject.setHolderTeacher(teacher); // compiler failed: package-protected
     teacher.addHeldSubject(subject);
     em.persist(teacher);
     em.persist(subject);
   }
}
