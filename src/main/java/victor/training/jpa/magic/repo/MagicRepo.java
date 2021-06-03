package victor.training.jpa.magic.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.repo.base.CustomJpaRepository;

public interface MagicRepo extends JpaRepository<Magic, Long> {
}
