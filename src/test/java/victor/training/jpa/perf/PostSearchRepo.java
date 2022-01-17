package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.perf.entity.PostSearchView;

public interface PostSearchRepo extends JpaRepository<PostSearchView, Long> {

}
