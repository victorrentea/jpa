package victor.training.jpa.perf;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Map;

public interface IDDocumentTypeRepo extends JpaRepository<IDDocumentType, Long> {
//  @QueryHints({
//          @QueryHint(name = "org.hibernate.cacheable", value = "true"),
//          @QueryHint(name = "org.hibernate.cacheRegion", value = "idDocumentTypes")
//  })
  @Cacheable("idDocByType")
  IDDocumentType findByLabel(String name);

}
