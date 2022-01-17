package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.domain.Specification;
import victor.training.jpa.perf.entity.Comment;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.entity.Post.PostType;
import victor.training.jpa.perf.entity.Post_;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

public class PostSpecifications {
   public static Specification<Post> all() {
      return (root, query, cb) -> cb.equal(cb.literal(1), 1);
   }
   public static Specification<Post> hasTitleLike(String name) {
      return (root, query, cb) ->
          cb.like(cb.upper(root.get(Post_.title)), "%" + name.toUpperCase() + "%"); // identic code to CriteriaMetamodel
   }

   public static Specification<Post> hasType(PostType postType) {
      return (root, query, cb) -> cb.equal(root.get(Post_.postType), postType);
   }
   public static Specification<Post> hasComments() {
      return (root, query, cb) -> {
         Subquery<Integer> subquery = query.subquery(Integer.class);
         Root<Post> subqueryRoot = subquery.from(Post.class);
         SetJoin<Post, Comment> join = subqueryRoot.join(Post_.comments);
         subquery.where(cb.equal(root.get(Post_.id), subqueryRoot.get(Post_.id)));
         return cb.exists(subquery.select(cb.literal(1)));
      };
   }
}
