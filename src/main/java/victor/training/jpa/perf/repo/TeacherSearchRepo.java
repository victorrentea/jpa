//package victor.training.jpa.perf.repo;
//
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.impl.JPAQuery;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Repository;
//import victor.training.jpa.app.domain.entity.*;
//import victor.training.jpa.perf.dto.PostSearchCriteria;
//import victor.training.jpa.perf.dto.PostSearchResult;
//import victor.training.jpa.perf.entity.Post;
//import victor.training.jpa.perf.entity.Post_;
//import victor.training.jpa.perf.entity.QPost;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.springframework.data.domain.Sort.Direction.ASC;
//import static victor.training.jpa.app.domain.entity.QCourseActivity.courseActivity;
//
//@Repository
//public class TeacherSearchRepo {
//   @PersistenceContext
//   private EntityManager entityManager;
//   @Autowired
//   private PostRepo teacherRepo;
//
//   public List<Post> jpqlConcat(PostSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
//      List<String> jpqlParts = new ArrayList<>();
//      jpqlParts.add("SELECT p FROM Post p WHERE 1=1");
//      Map<String, Object> params = new HashMap<>();
//
//      if (searchCriteria.title != null) {
//         jpqlParts.add("AND UPPER(p.title) LIKE UPPER('%' || :title || '%')");
//         params.put("title", searchCriteria.title);
//      }
//
//      if (searchCriteria.postType != null) {
//         jpqlParts.add("AND t.postType = :postType");
//         params.put("postType", searchCriteria.postType);
//      }
//      if (searchCriteria.havingComments) {
//         jpqlParts.add("AND EXISTS (SELECT 1 FROM Post p JOIN p.comment)");
//      }
//
//      TypedQuery<Post> query = entityManager.createQuery(String.join("\n", jpqlParts), Post.class);
//      for (String param : params.keySet()) {
//         query.setParameter(param, params.get(param));
//      }
//      return query.getResultList();
//   }
//
//   public List<PostSearchResult> searchProjectionUsingCriteriaMetamodel() {
//      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//      CriteriaQuery<PostSearchResult> criteriaQuery = cb.createQuery(PostSearchResult.class);
//      Root<Post> root = criteriaQuery.from(Post.class);
//      criteriaQuery.select(cb.construct(PostSearchResult.class,
//          root.get(Post_.id), root.get(Post_.title), root.get(Post_.)));
//      return entityManager.createQuery(criteriaQuery).getResultList();
//   }
//
//   public List<Post> criteriaApi(PostSearchCriteria searchCriteria) {
//      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//      CriteriaQuery<Post> criteriaQuery = cb.createQuery(Post.class);
//      Root<Post> root = criteriaQuery.from(Post.class);
//
//      List<Predicate> predicates = new ArrayList<>();
//
//      if (searchCriteria.postType != null) {
//         // TODO extract Spring Specifications starting from cb.equal ...
//         predicates.add(cb.equal(root.get(Post_.postType), searchCriteria.postType));
//         predicates.add(cb.equal(root.get("postType"), searchCriteria.postType));// without metamodel
//      }
//
//      if (searchCriteria.title != null) {
//         predicates.add(cb.like(cb.upper(root.get(Post_.title)), "%" + searchCriteria.title.toUpperCase() + "%"));
//      }
//
//      if (searchCriteria.havingComments) {
//         Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
//         Root<CourseActivity> subqueryRoot = subquery.from(CourseActivity.class);
//         SetJoin<CourseActivity, Post> join = subqueryRoot.join(CourseActivity_.teachers);
//         subquery.where(cb.equal(root.get(Post_.id), join.get(Post_.id)));
//         predicates.add(cb.exists(subquery.select(cb.literal(1))));
//      }
//      // Exception on the way: java.lang.IllegalStateException: No explicit selection and an implicit one could not be determined
//
//      criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
//
//      return entityManager.createQuery(criteriaQuery).getResultList();
//   }
//
//   public List<Post> specifications(PostSearchCriteria searchCriteria) {
//      Specification<Post> spec = PostSpecifications.all();
//      if (searchCriteria.title != null) {
//         spec = spec.and(PostSpecifications.hasNameLike(searchCriteria.title));
//      }
//      if (searchCriteria.postType != null) {
//         spec = spec.and(PostSpecifications.hasGrade(searchCriteria.postType));
//      }
//      if (searchCriteria.havingComments) {
//         spec = spec.and(PostSpecifications.havingComments);
//      }
//      // xtra: pagination
//      return teacherRepo.findAll(spec, PageRequest.of(0, 10, ASC, "title")).getContent();
//   }
//
//   public List<Post> queryDSL(PostSearchCriteria searchCriteria) {
//      JPAQuery<?> query = new JPAQuery<Void>(entityManager);
//
//      QPost teacher = QPost.teacher;
//      JPAQuery<Post> outerQuery = query.select(teacher)
//          .from(teacher);
//
//
//      List<com.querydsl.core.types.Predicate> predicates = new ArrayList<>();
//      if (searchCriteria.postType != null) {
//         predicates.add(teacher.postType.eq(searchCriteria.postType));
//      }
//      if (searchCriteria.title != null) {
//         predicates.add(teacher.title.upper()
//             .like("%" + searchCriteria.title.toUpperCase() + "%"));
//      }
//      if (searchCriteria.havingComments) {
//         QPost tt = new QPost("tt");
//
//         predicates.add(new JPAQuery<Integer>()
//             .select(Expressions.constant(1))
//             .from(courseActivity)
//             .join(courseActivity.teachers, tt)
//             .where(tt.id.eq(teacher.id)).exists());
//      }
//
//      return outerQuery
//          .where(predicates.toArray(new com.querydsl.core.types.Predicate[0]))
//          .fetchAll()
//          .fetch();
//   }
//}
