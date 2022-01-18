package victor.training.jpa.perf.repo;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import victor.training.jpa.perf.dto.PostSearchCriteria;
import victor.training.jpa.perf.dto.PostSearchResult;
import victor.training.jpa.perf.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.ASC;

@FieldNameConstants
@Repository
public class PostSearchRepo {
   @PersistenceContext
   private EntityManager entityManager;
   @Autowired
   private PostRepo postRepo;

   public List<Post> jpqlConcat(PostSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
//      PostSearchRepo.Fields.
      List<String> jpqlParts = new ArrayList<>();
      jpqlParts.add("SELECT p FROM Post p WHERE 1=1");
      Map<String, Object> params = new HashMap<>();

      if (searchCriteria.title != null) {
         jpqlParts.add("AND UPPER(p.title) LIKE UPPER('%' || :title || '%')");
         params.put("title", searchCriteria.title);
      }

      if (searchCriteria.postType != null) {
         jpqlParts.add("AND p.postType = :postType");
         params.put("postType", searchCriteria.postType);
      }
      if (searchCriteria.havingComments) {
         jpqlParts.add("AND EXISTS (SELECT 1 FROM Post pp INNER JOIN p.comments WHERE pp.id = p.id)");
      }

      String finalJpql = String.join("\n", jpqlParts);
      TypedQuery<Post> query = entityManager.createQuery(finalJpql, Post.class);
      for (String param : params.keySet()) {
         query.setParameter(param, params.get(param));
      }
      return query.getResultList();
   }

   public List<PostSearchResult> searchProjectionUsingCriteriaMetamodel() {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<PostSearchResult> criteriaQuery = cb.createQuery(PostSearchResult.class);
      Root<Post> root = criteriaQuery.from(Post.class);
      criteriaQuery.select(cb.construct(PostSearchResult.class,
          root.get(Post_.id), root.get(Post_.title), root.get(Post_.publishDate)));
      return entityManager.createQuery(criteriaQuery).getResultList();
   }

   public List<Post> criteriaApi(PostSearchCriteria searchCriteria) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Post> criteriaQuery = cb.createQuery(Post.class);
      Root<Post> root = criteriaQuery.from(Post.class);

      List<Predicate> predicates = new ArrayList<>();

      if (searchCriteria.postType != null) {
         // TODO extract Spring Specifications starting from cb.equal ...
         predicates.add(cb.equal(root.get(Post_.postType), searchCriteria.postType));
         predicates.add(cb.equal(root.get("postType"), searchCriteria.postType));// without metamodel
      }

      if (searchCriteria.title != null) {
         predicates.add(cb.like(cb.upper(root.get(Post_.title)),
             "%" + searchCriteria.title.toUpperCase() + "%"));
      }

      if (searchCriteria.havingComments) {
         Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
         Root<Post> subqueryRoot = subquery.from(Post.class);
         SetJoin<Post, Comment> join = subqueryRoot.join(Post_.comments);
         subquery.where(cb.equal(root.get(Post_.id), subqueryRoot.get(Post_.id)));
         predicates.add(cb.exists(subquery.select(cb.literal(1))));
      }
      // Exception on the way: java.lang.IllegalStateException: No explicit selection and an implicit one could not be determined

      criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));

      return entityManager.createQuery(criteriaQuery).getResultList();
   }

   public List<Post> specifications(PostSearchCriteria searchCriteria) {
      Specification<Post> spec = PostSpecifications.all();
      if (searchCriteria.title != null) {
         spec = spec.and(PostSpecifications.hasTitleLike(searchCriteria.title));
      }
      if (searchCriteria.postType != null) {
         spec = spec.and(PostSpecifications.hasType(searchCriteria.postType));
      }
      if (searchCriteria.havingComments) {
         spec = spec.and(PostSpecifications.hasComments());
      }
      // xtra: pagination
      return postRepo.findAll(spec, PageRequest.of(0, 10, ASC, "title")).getContent();
   }

   public List<Post> queryDSL(PostSearchCriteria searchCriteria) {
      JPAQuery<?> query = new JPAQuery<Void>(entityManager);

      QPost post = QPost.post;
      JPAQuery<Post> outerQuery = query.select(post)
          .from(post);


      List<com.querydsl.core.types.Predicate> predicates = new ArrayList<>();
      if (searchCriteria.postType != null) {
         predicates.add(post.postType.eq(searchCriteria.postType));
      }
      if (searchCriteria.title != null) {
         predicates.add(post.title.upper()
             .like("%" + searchCriteria.title.toUpperCase() + "%"));
      }
      if (searchCriteria.havingComments) {
         QPost pp = new QPost("tt");
         QComment c = new QComment("c");

         predicates.add(new JPAQuery<Integer>()
             .select(Expressions.constant(1))
             .from(pp)
             .join(pp.comments, c)
             .where(pp.id.eq(post.id)).exists());
      }

      return outerQuery
          .where(predicates.toArray(new com.querydsl.core.types.Predicate[0]))
          .fetchAll()
          .fetch();
   }
}
