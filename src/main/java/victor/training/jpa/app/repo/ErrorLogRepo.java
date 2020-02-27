package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.app.domain.entity.ErrorLog;

public interface ErrorLogRepo extends JpaRepository<ErrorLog, Long> {
}
