package victor.training.jpa.app.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;

import java.util.List;

import static victor.training.jpa.app.repo.TeacherSpecifications.*;
import static victor.training.jpa.app.repo.TeacherSpecifications.hasGrade;

@RequiredArgsConstructor
@Service
public class BusinessClass {
   private final TeacherRepo teacherRepo;

   public void method() {
      List<Teacher> teachers = teacherRepo.findAll(
            hasGrade(Grade.CONF).or(hasNameLike("a")));
   }
}
