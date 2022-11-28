package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParentRepo extends JpaRepository<Parent, Long> {
  @Query("FROM Parent p LEFT JOIN FETCH p.children")
  List<Parent> findAllWithChildren();


}
