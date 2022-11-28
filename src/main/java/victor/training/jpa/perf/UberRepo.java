package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UberRepo extends JpaRepository<UberEntity, Long> {
//  @Query
//  void myOwnQuery();
}
