package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface ParentRepo extends JpaRepository<Parent, Long> {
   @Query("SELECT  DISTINCT  p FROM Parent p LEFT JOIN FETCH p.children")
   Stream<Parent> find();
}
