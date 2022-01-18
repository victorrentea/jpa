package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import victor.training.jpa.perf.entity.Country;

import javax.persistence.QueryHint;
import java.util.List;

public interface CountryRepo extends JpaRepository<Country, Long> {
   //@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
   @Override
   List<Country> findAll();
}
