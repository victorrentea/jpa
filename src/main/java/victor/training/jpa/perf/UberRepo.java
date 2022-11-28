package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UberRepo extends JpaRepository<UberEntity, Long> {
  // NEVER do this: select full entities for hot searches
  @Query("SELECT u FROM UberEntity u WHERE u.firstName LIKE ?1")
  List<UberEntity> search(String john);
}
