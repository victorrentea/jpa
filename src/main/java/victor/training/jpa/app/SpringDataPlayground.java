package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.StudentsGroup;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.repo.LabRepo;
import victor.training.jpa.app.repo.StudentGroupRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpringDataPlayground {
   private final TeacherRepo repo;
   private final StudentGroupRepo studentGroupRepo;
   private final LabRepo labRepo;

   @Transactional
   public void method() {
      repo.save(new Teacher().setName("JOHN").setGrade(Grade.PROFESSOR));
      Optional<Teacher> optPanica = repo.findByNameLikeAndGrade("%oh%", Grade.PROFESSOR);
      optPanica.ifPresent(x -> System.out.println("CEVA: " + x));

      Teacher byId = repo.findById(1L).get();//.orElseThrow(() -> new RuntimeException("Nu e!"));
      System.out.println(optPanica);
      Teacher oneById = repo.findOneById(1L);
      System.out.println(oneById);

//      repo.savean

      long groupIdDePeDto = 2L;
//      StudentsGroup ref = studentGroupRepo.findOneById(groupIdDePeDto);
      StudentsGroup ref = studentGroupRepo.getOne(groupIdDePeDto);
      LabActivity lab = new LabActivity()
          .setGroup(ref);
      labRepo.save(lab);

      System.out.println(ref.getClass());

//      Teacher err = repo.findOneById(2L);
//      System.out.println(err);
   }
}
