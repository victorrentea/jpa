package victor.training.jpa.perf.repo;

import victor.training.jpa.perf.dto.PostSearchCriteria;
import victor.training.jpa.perf.entity.Post;

import java.util.List;

public interface PostRepoCustom {

   List<Post> search(PostSearchCriteria searchCriteria);

}
