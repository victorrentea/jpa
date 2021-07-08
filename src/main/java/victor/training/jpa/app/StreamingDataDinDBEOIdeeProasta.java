package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class StreamingDataDinDBEOIdeeProasta { // TODO invata frate Spring Batch nu mai fugi
   private final TeacherRepo teacherRepo;
   private final EntityManager em;

   @Transactional//(readOnly = true) insuficienta
   public void method() {
      teacherRepo.totiCasMulti()
          .peek(entity -> em.detach(entity)) // evita OutOfMemory
          .forEach(t -> {
         t.setGrade(Grade.ASSISTENT); // ar necesita cf spec JPA sa se scrie inapoi in baza modificarea. CAND? La finalul Tx de 1M de randuri
         // Hib va tine minte by default o copie din toate entitatile pe care ti le da, ca nu are de unde sa stie pecare o modific -->> OutOfMemory
         System.out.println("Scriu in fisier : " + t);
      });
   }

}
