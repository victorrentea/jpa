package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.domain.entity.A;

import java.util.List;

public interface ARepo extends JpaRepository<A, Long> {
   @Query("SELECT a FROM A a left join fetch a.bList b left join fetch b.cList c")
   //WHERE (SELECT 1 FROM A aa JOIN B bb ON (aa.id = bb.id))")
   List<A> getAllAWithChildren();
}
