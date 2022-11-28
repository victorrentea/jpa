package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChildRepo extends JpaRepository<Child,Long> {

  @Query("SELECT p.id, c.name FROM Parent p JOIN p.children c WHERE p.id IN (?1)")
  List<Object[]> getParentIdToChildName(List<Long> parentIds);
}
