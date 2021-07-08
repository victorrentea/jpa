package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.app.domain.entity.C;
import victor.training.jpa.perf.Country;

import java.util.List;

interface  CountryRepo extends JpaRepository<Country, Long> {}

@RequiredArgsConstructor
public class CacheIntro {
   private final CountryRepo repo;

   @Cacheable("countries")
   public List<Country> findAllCountries() {
      return repo.findAll();
   }

   @CacheEvict(value = "countries",allEntries = true)
   public void addCountry(Country country) {
      repo.save(country);
   }
}
