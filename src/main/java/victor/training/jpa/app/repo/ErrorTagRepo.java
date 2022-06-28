package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.app.entity.ErrorTag;

public interface ErrorTagRepo extends JpaRepository<ErrorTag, Long> {

}
