package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;

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
     teacher.addHeldSubject(subject);
     System.out.println("Inainte:"+teacher.getId());
     em.persist(teacher);
     System.out.println("Dupa:"+teacher.getId()); // cf legii dupa persist TRE SA AI ID SETAT
     em.persist(subject);
   }


  @Transactional
  public void writeBehind() {
    em.persist(new StudentsYear("ONE")
        .add(new StudentsGroup("SG1")));
    log.info("--- End of method");
  }

  public void autoSave() {
    StudentsYear entity = em.find(StudentsYear.class,1L);
    entity.setCode("TWO");
  }

  public void lazyLoading() {
    StudentsYear entity = em.find(StudentsYear.class, 1L);
    log.info("Message: " /* +entity*/);
  }
}
