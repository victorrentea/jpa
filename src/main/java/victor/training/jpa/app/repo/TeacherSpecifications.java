package victor.training.jpa.app.repo;

import org.springframework.data.jpa.domain.Specification;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.domain.entity.Teacher_;

public class TeacherSpecifications {
   public static Specification<Teacher> hasNameLike(String name) {
      return (root, query, cb) ->
          cb.like(cb.upper(root.get(Teacher_.name)), "%" + name.toUpperCase() + "%"); // identic code to CriteriaMetamodel
   }

   public static Specification<Teacher> hasGrade(Grade grade) {
      return (root, query, cb) ->
          cb.lessThan(root.get(Teacher_.grade), grade);
   }
}
