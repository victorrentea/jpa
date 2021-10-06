package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UberRepo extends JpaRepository<UberEntity, Long> {
//   @Query("SELECT u.id, u.name, u.cnp FROM UberEntity u")
//   List<Object[]> search();
   @Query("SELECT new victor.training.jpa.perf.UberEntitySearchResultDto(u.id, u.name, c.name, u.cnp) " +
          " FROM UberEntity u JOIN Country c ON c.id = u.originCountryId ")
   List<UberEntitySearchResultDto> search();
}
