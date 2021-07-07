package victor.training.jpa.app.repo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.domain.entity.A;
import victor.training.jpa.app.domain.entity.B;

import java.util.List;
import java.util.Set;

public interface ARepo extends JpaRepository<A, Long> {
   @Query("SELECT a FROM A a left join fetch a.bList b left join fetch b.cList c")
   //WHERE (SELECT 1 FROM A aa JOIN B bb ON (aa.id = bb.id))")
   List<A> getAllAWithChildren(); // niciodata nu PageRequest request + JOIN in JPQL/SQL ca nu mai are sens paginarea

   // descompui query-ul in 2
   @Query("SELECT a.id FROM A a")
   List<Long> getParents(PageRequest pageRequest);

   @Query("SELECT b FROM A a left join B b WHERE a.id IN ?1")
   List<B> getChildrenOfManyParents(List<Long> ids);


   @Query("SELECT distinct new victor.training.jpa.app.repo.BCNameDto(b.name, c.name) FROM B b left join b.cList c")
   Set<BCNameDto> findAllBC(PageRequest page);
}
