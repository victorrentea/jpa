package victor.training.jpa.app.repo;

import org.springframework.data.jpa.domain.Specification;
import victor.training.jpa.app.entity.CourseActivity;
import victor.training.jpa.app.entity.CourseActivity_;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;
import victor.training.jpa.app.entity.Teacher_;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;

public class TeacherSpecifications {
   public static Specification<Teacher> all() {
      return (root, query, cb) -> cb.equal(cb.literal(1), 1);
   }
   public static Specification<Teacher> hasNameLike(String name) {
      return (root, query, cb) ->
          cb.like(cb.upper(root.get(Teacher_.name)), "%" + name.toUpperCase() + "%"); // identic code to CriteriaMetamodel
   }

   public static Specification<Teacher> hasGrade(Grade grade) {
      return (root, query, cb) -> cb.equal(root.get(Teacher_.grade), grade);
   }
   public static Specification<Teacher> teachingCourses() {
      return (root, query, cb) -> {
         Subquery<Integer> subquery = query.subquery(Integer.class);
         Root<CourseActivity> subqueryRoot = subquery.from(CourseActivity.class);
         SetJoin<CourseActivity, Teacher> join = subqueryRoot.join(CourseActivity_.teachers);
         subquery.where(cb.equal(root.get(Teacher_.id), join.get(Teacher_.id)));
         return cb.exists(subquery.select(cb.literal(1)));
      };
   }
}
