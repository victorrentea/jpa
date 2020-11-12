package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.app.domain.entity.ErrorLog;

import java.util.List;

public interface ErrorLogRepo extends JpaRepository<ErrorLog, Long> {

   List<ErrorLog> findByMessageLike(String part);
}
