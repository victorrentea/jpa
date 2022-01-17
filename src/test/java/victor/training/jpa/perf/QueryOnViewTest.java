package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.entity.Comment;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.entity.Post.PostType;
import victor.training.jpa.perf.repo.PostRepo;
import victor.training.jpa.perf.repo.PostSearchViewRepo;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
public class QueryOnViewTest {
   @Autowired
   private PostSearchViewRepo searchRepo;
   @Autowired
   private PostRepo postRepo;

   @BeforeEach
   public void persistData() {
      postRepo.deleteAll();
      postRepo.save(new Post("Victor")
          .setPostType(PostType.PHILOSOPHY)
          .addComment(new Comment("Emma"))
          .addComment(new Comment("Vlad"))
      );
      postRepo.save(new Post("Sanda")
          .addComment(new Comment("Maria"))
      );

      TestTransaction.end();
      TestTransaction.start();
   }

   @Test
   public void entityOnView() {
      searchRepo.findAll().forEach(System.out::println);

      assertThat(searchRepo.findAll())
          .anyMatch(ps -> ps.getCommentTitles().contains("Vlad,Emma"))
          .anyMatch(ps -> ps.getCommentTitles().contains("Maria"));

      assertThat(searchRepo.queryViaRootEntityModel())
          .hasSize(1)
          .anyMatch(ps -> ps.getCommentTitles().contains("Vlad,Emma"));
   }

}

