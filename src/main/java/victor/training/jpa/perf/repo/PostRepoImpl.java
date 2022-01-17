package victor.training.jpa.perf.repo;

import victor.training.jpa.perf.dto.PostSearchCriteria;
import victor.training.jpa.perf.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

public class PostRepoImpl implements PostRepoCustom {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public List<Post> search(PostSearchCriteria searchCriteria) {
      // TODO query directly TeacherSearchResult objects
      // TODO: see TeacherSearchRepo for alternatives of writing a dynamic query
      return Collections.emptyList();
   }
}




