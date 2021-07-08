package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.repo.TeacherRepo;
import victor.training.jpa.app.repo.TeacherSpecifications;

import java.util.List;

import static victor.training.jpa.app.repo.TeacherSpecifications.*;
import static victor.training.jpa.app.repo.TeacherSpecifications.hasNameLike;

@Component
@RequiredArgsConstructor
public class TeacherService {
   // Holy domain logic !!

   private final TeacherRepo repo;
   public void method() {
      List<Teacher> all = repo.findAll(hasGrade(Grade.PROFESSOR).or(hasNameLike("%Joh%")));

   }
}
