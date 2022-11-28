package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ParentRepo extends JpaRepository<Parent, Long> {
  @Query("FROM Parent p LEFT JOIN FETCH p.children")
  Set<Parent> findAllWithChildren();


  @Query(value = "select parent0_.name        as pn,\n" +
         "       children1_.name      as cn\n" +
         "from parent parent0_\n" +
         "         left outer join child children1_ on parent0_.id = children1_.parent_id", nativeQuery = true)
  List<Object[]> ourNativeQuery90();
}
