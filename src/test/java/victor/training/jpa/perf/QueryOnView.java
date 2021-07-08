package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
@Sql("/create-view.sql")
public class QueryOnView {
   @Autowired
   private ParentSearchRepo searchRepo;
   @Autowired
   private EntityManager em;

   @BeforeEach
   public void persistData() {
      em.persist(new Parent("Victor")
          .addChild(new Child("Emma"))
          .addChild(new Child("Vlad"))
      );
      TestTransaction.end();
      TestTransaction.start();
   }

   @Test
   public void entityOnView() {
      searchRepo.findAll().forEach(System.out::println);
      Assertions.assertThat(searchRepo.findAll())
          .anyMatch(ps -> ps.getChildrenNames().contains("Vlad,Emma"));
   }
}



interface ParentSearchRepo extends JpaRepository<ParentSearchView, Long> {
//   @Query("SELECT p, pv FROM Parent p JOIN ParentSearchView pv ON pv.id = p.id" +
//          " WHERE p.name LIKE ?1")
//   List<Object[]> met(String namePart);
}