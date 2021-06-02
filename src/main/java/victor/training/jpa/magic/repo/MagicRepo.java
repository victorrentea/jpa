package victor.training.jpa.magic.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.repo.base.CustomJpaRepository;

public interface MagicRepo extends CustomJpaRepository<Magic, Long>, JpaSpecificationExecutor<Magic> {
   @Query("FROM Magic m where m.id = ?1")
   Magic myQuery(Long id);
}
