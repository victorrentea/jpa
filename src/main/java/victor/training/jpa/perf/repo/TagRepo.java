package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.perf.entity.Tag;

public interface TagRepo extends JpaRepository<Tag, Long> {
}
