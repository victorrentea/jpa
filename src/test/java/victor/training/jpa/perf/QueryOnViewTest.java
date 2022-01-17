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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.entity.Comment;
import victor.training.jpa.perf.entity.Post;

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
      em.persist(new Post("Victor")
          .addComment(new Comment("Emma"))
          .addComment(new Comment("Vlad"))
      );
      em.persist(new Post("Sanda")
          .addComment(new Comment("Maria"))
      );
   }

   @Test
   @Sql("/create-view.sql")
   public void entityOnView() {
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
class ParentSearchView {
   @Id
   private Long id;
   private String name;
   private String childrenNames;
}


interface ParentSearchRepo extends JpaRepository<ParentSearchView, Long> {
   @Query("SELECT pv " +
          " FROM ParentSearchView pv JOIN Post p ON pv.id = p.id " +
          " JOIN p.children c" +
          " WHERE c.age < 3") // you can go back to your Entity to select via your entity model
   List<ParentSearchView> queryViaRootEntityModel();
}