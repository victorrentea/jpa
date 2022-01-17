//package victor.training.jpa.perf.repo;
//
//import org.springframework.data.jpa.domain.Specification;
//import victor.training.jpa.app.domain.entity.CourseActivity;
//import victor.training.jpa.app.domain.entity.CourseActivity_;
//import victor.training.jpa.app.domain.entity.Teacher;
//import victor.training.jpa.app.domain.entity.Teacher.Grade;
//import victor.training.jpa.app.domain.entity.Teacher_;
//import victor.training.jpa.perf.entity.Post;
//import victor.training.jpa.perf.entity.Post.PostType;
//import victor.training.jpa.perf.entity.Post_;
//
//import javax.persistence.criteria.Root;
//import javax.persistence.criteria.SetJoin;
//import javax.persistence.criteria.Subquery;
//
//public class TeacherSpecifications {
//   public static Specification<Post> all() {
//      return (root, query, cb) -> cb.equal(cb.literal(1), 1);
//   }
//   public static Specification<Post> hasNameLike(String name) {
//      return (root, query, cb) ->
//          cb.like(cb.upper(root.get(Post_.name)), "%" + name.toUpperCase() + "%"); // identic code to CriteriaMetamodel
//   }
//
//   public static Specification<Post> hasGrade(PostType grade) {
//      return (root, query, cb) -> cb.equal(root.get(Post_.post), grade);
//   }
//   public static Specification<Post> teachingCourses() {
//      return (root, query, cb) -> {
//         Subquery<Integer> subquery = query.subquery(Integer.class);
//         Root<CourseActivity> subqueryRoot = subquery.from(CourseActivity.class);
//         SetJoin<CourseActivity, Post> join = subqueryRoot.join(CourseActivity_.teachers);
//         subquery.where(cb.equal(root.get(Post_.id), join.get(Post_.id)));
//         return cb.exists(subquery.select(cb.literal(1)));
//      };
//   }
//}
