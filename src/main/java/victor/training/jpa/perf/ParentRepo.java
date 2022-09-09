package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ParentRepo extends JpaRepository<Parent, Long> {
    // idee proasta: explozie cardinala : children x childrenAdoptati
    @Query("SELECT p from Parent p LEFT JOIN FETCH p.children"
    )
    Set<Parent> findAllWithChildren();

    @Query(value = "select p.name, count(c.id) " +
           "from parent p " +
           "         left outer join child c on p.id = c.parent_id" +
           "group by p.name", nativeQuery = true)
    List<Object[]> findSmecher();
}
