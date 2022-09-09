package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ParentRepo extends JpaRepository<Parent, Long> {
    // idee proasta: explozie cardinala : children x childrenAdoptati
    @Query("SELECT p from Parent p LEFT JOIN FETCH p.children"
//           + " LEFT JOIN FETCH p.childrenAdoptati"
    )
    Set<Parent> findAllWithChildren();
}
