package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.perf.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
