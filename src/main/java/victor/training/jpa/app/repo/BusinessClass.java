package victor.training.jpa.app.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BusinessClass {
   private final TeacherRepo teacherRepo;

   public void method() {
      Specification<Teacher> spec = TeacherSpecifications.hasGrade(Grade.CONF)
          .or(TeacherSpecifications.hasNameLike("a"));
      List<Teacher> teachers = teacherRepo.findAll(spec);
   }
}
