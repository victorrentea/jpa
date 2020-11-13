package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ContactChannel;
import victor.training.jpa.app.domain.entity.ContactChannel.Type;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.repo.TeacherRepo;

@Component
@RequiredArgsConstructor
@Slf4j
public class ElementCollectionPLay //implements CommandLineRunner
{
   private final Work work;

   public void run(String... args) throws Exception {
      log.debug("Coll start");
      Long id = work.insertTeacher();
      work.changeTeacher(id);
      log.debug("Coll end");
   }
}

@Component
@RequiredArgsConstructor

class Work {
   private final TeacherRepo teacherRepo;
   public Long insertTeacher() {
      return teacherRepo.save(new Teacher()
          .addHeldSubject(new Subject("B"))
          .addHeldSubject(new Subject("A"))
          .addHeldSubject(new Subject("C"))
      ).getId();
   }

   @Transactional
   public void changeTeacher(Long id) {
      Teacher teacher = teacherRepo.findById(id).get();
      System.out.println(teacher.getHeldSubjects());
      teacher.removeSubject(teacher.getHeldSubjects().iterator().next());


   }
}