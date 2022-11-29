package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface IDDocumentTypeRepo extends JpaRepository<IDDocumentType, Long> {
  IDDocumentType findByLabel(String name);

}
