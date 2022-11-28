package victor.training.jpa.perf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ParentRepo extends JpaRepository<Parent, Long> {
  @Query("FROM Parent p LEFT JOIN FETCH p.children")
  Set<Parent> findAllWithChildren();


  @Query(value = "select p.ID, P.NAME, STRING_AGG(c.NAME, ',') within group (order by c.name desc) children_names\n" +
                 "from PARENT P\n" +
                 "    left join CHILD C on P.ID = C.PARENT_ID\n" +
                 "group by p.ID, P.NAME",
          nativeQuery = true)
  List<Object[]> ourNativeQuery90();

  @Query("SELECT p FROM Parent p")
  List<ParentForUC32> findAllForUC32();
}
