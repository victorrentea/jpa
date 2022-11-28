package victor.training.jpa.perf;

import lombok.Data;
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

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
public class QueryOnViewTest {
   @Autowired
   private ParentSearchRepo searchRepo;
   @Autowired
   private EntityManager em;

   @BeforeEach
   public void persistData() {
      em.persist(new Parent("Victor")
          .addChild(new Child("Emma").setAge(7))
          .addChild(new Child("Vlad").setAge(3))
      );
      em.persist(new Parent("Sanda")
          .addChild(new Child("Maria").setAge(10))
      );
      em.flush(); // <- usually bad practice.
   }

   @Test
   @Sql("/create-view.sql")
   public void entityOnView() {
      // by default Hibernate figures out itself when to flush data to the database (write the changes)
      // but here, Hibernate did not understand that the findAll is influenced by the persist in the before.
      // because tou are SELECTIGN from a DIFFERENT entity.
      searchRepo.findAll().forEach(System.out::println);

      Assertions.assertThat(searchRepo.findAll())
          .anyMatch(ps -> ps.getChildrenNames().contains("Vlad,Emma"));

      Assertions.assertThat(searchRepo.queryViaRootEntityModel())
          .hasSize(1)
          .anyMatch(ps -> ps.getChildrenNames().contains("Vlad,Emma"));
   }
}
@Table(name = "PARENT_SEARCH_VIEW")
@Entity
@Data
class ParentSearchView { // entity mapped on view
   @Id
   private Long id;
   private String name;
   private String childrenNames;
}


interface ParentSearchRepo extends JpaRepository<ParentSearchView, Long> {
   // on one side, I SELECT from a VIEW able to use aggregate vunctioons
   // on the other side, I can still use my entity model to write filtering criterias

   @Query("SELECT pview " +
          " FROM ParentSearchView pview JOIN Parent p ON pview.id = p.id " +
          " JOIN p.children c" +
          " WHERE c.age < 3") // you can go back to your Entity to select via your entity model
   List<ParentSearchView> queryViaRootEntityModel();
}