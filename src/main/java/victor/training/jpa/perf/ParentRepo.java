package victor.training.jpa.perf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ParentRepo extends JpaRepository<Parent, Long> {
  @Query("FROM Parent p") // LEFT JOIN FETCH p.children2 dangerous => cartesian explosion (too many rows)
  Page<Parent> findAllWithChildren(Pageable pageable);

  @Query("SELECT p FROM Parent p")
  @EntityGraph("Parent.withChildrenNames")
  List<Parent> findParentsByGraph();

  @Query(value = "select p.ID, P.NAME, STRING_AGG(c.NAME, ',') within group (order by c.name desc) children_names\n" +
                 "from PARENT P\n" +
                 "    left join CHILD C on P.ID = C.PARENT_ID\n" +
                 "group by p.ID, P.NAME",
          nativeQuery = true)
  List<Object[]> ourNativeQuery90();

  @Query("SELECT p FROM Parent p")
  List<ParentForUC32> findAllForUC32();

  @Query("SELECT p FROM Parent p")
  Stream<Parent> streamAll();

  @Query("SELECT c FROM Child c WHERE c.age > 10")
  List<Child> getChildrenOver10();

  @Query("SELECT p FROM Parent p WHERE EXISTS (SELECT 1 FROM Child c WHERE  c.parent.id= p.id AND c.age > 10)")
  Page<Parent> getParentsWithChildrenOver10(Pageable pageable);

  @Query("SELECT c.parent.id, c FROM Child c WHERE c.parent.id IN (?1) AND c.age > 10")
  List<Object[]> getParentsWithChildrenOver10(List<Long> parentIds);
}

class InjavaCode{
  private final ParentRepo repo;

  InjavaCode(ParentRepo repo) {
    this.repo = repo;
  }

  public void method() {
    List<Child> children = repo.getChildrenOver10();
    Map<Parent, List<Child>> parentToChildrenList =
            children.stream().collect(Collectors.groupingBy(Child::getParent));
//
//    Map<Parent, List<Child>> parentToChildrenList = new HashMap<>();
//    for (Child child : children) {
//
//      for (p:child.getParents()) {
//        parentToChildrenList.put(p, child in the list)
//      }
//    }

  }
}
