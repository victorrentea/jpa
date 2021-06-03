package victor.training.jpa.magic.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.repo.base.CustomJpaRepository;

import java.util.Optional;

public interface MagicRepo extends CustomJpaRepository<Magic, Long>, MagicRepoCustom, JpaSpecificationExecutor<Magic>, QuerydslPredicateExecutor<Magic> {
   @Query("FROM Magic where name LIKE '%' || ?1 || '%'")
   Optional<Magic> getByNameSimilar(String namePart);

}
