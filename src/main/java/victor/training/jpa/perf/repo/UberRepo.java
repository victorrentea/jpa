package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.perf.UberDto;
import victor.training.jpa.perf.entity.UberEntity;

public interface UberRepo extends JpaRepository<UberEntity, Long> {
   @Query("SELECT new victor.training.jpa.perf.UberDto(u.id, u.name, c.name)" +
          " FROM UberEntity u LEFT JOIN Country c ON c.id = u.originCountryId  " +
          " WHERE u.id = ?1")
   UberDto loadForUI(Long id);
}



//   asically could be a good practise to join entitioes when the most of thje call need data from all of them, otherwiste perform specific join when needed. Am I wrong?
