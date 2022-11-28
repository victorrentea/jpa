package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UberRepo extends JpaRepository<UberEntity, Long> {
  // this repo method canot be part of the domain (Dep Inversion Principle in concentric architecture)
  @Query("SELECT new victor.training.jpa.perf.UberSearchResult(" +
         "u.id, u.firstName, u.lastName, oc.name) " +
         "FROM UberEntity u JOIN Country oc ON oc.id = u.originCountryId "+
         " WHERE u.firstName LIKE ?1")
  List<UberSearchResult> search(String john);
}
