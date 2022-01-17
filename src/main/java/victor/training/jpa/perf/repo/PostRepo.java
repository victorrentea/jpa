package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.perf.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long>, PostRepoCustom {
}
