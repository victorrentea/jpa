package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UberEntityRepo extends JpaRepository<UberEntity, Long> {
}
