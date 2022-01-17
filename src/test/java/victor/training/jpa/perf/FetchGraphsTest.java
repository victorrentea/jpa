package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.entity.*;
import victor.training.jpa.perf.repo.PostRepo;
import victor.training.jpa.perf.repo.UserRepo;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
public class FetchGraphsTest {

   @Autowired
   private EntityManager entityManager;
   @Autowired
   private PostRepo postRepo;
   @Autowired
   private UserRepo userRepo;

   @BeforeEach
   public void persistData() {
      postRepo.deleteAll();
      userRepo.deleteAll();
      User alice = userRepo.save(new User("alice"));
      User bob = userRepo.save(new User("bob"));
      User charlie = userRepo.save(new User("charlie"));

      postRepo.save(new Post("ORM Mapping")
          .setAuthor(charlie)
          .setPublishDate(now())
          .addComment(new Comment("Obvious").setUser(alice))
      );
      postRepo.save(new Post("JPA Performance")
          .setAuthor(charlie)
          .setPublishDate(now().minusDays(1))
          .addComment(new Comment("Great").setUser(alice))
          .addComment(new Comment("Wow").setUser(bob))
      );

      TestTransaction.end();
      TestTransaction.start();
   }


   @Test
   void analyzeComments() {
      Set<Post> targetPosts = postRepo.findPostsAfter(now().minusYears(1));

      Map<String, Integer> commenters = new HashMap<>();
      for (Post targetPost : targetPosts) {
         if (!targetPost.isHeavilyCommented())
            for (Comment comment : targetPost.getComments()) {
               String username = comment.getUser().getUsername();
               commenters.merge(username, 1, Integer::sum);
            }
      }

      assertThat(commenters).containsExactlyInAnyOrderEntriesOf(Map.of(
          "bob", 1,
          "alice", 2));

      System.out.println("-after (lazy loaded)-");
      targetPosts.forEach(p -> System.out.println(p.getAuthor()));
   }

   @Test
   void springProjections() {
      Set<Post1> list = postRepo.findPostsAfter2(now().minusYears(1));
      System.out.println("Found: "+ list);
      for (Post1 post1 : list) {
         System.out.println(post1.getTitle());
         for (Comment1 comment : post1.getComments()) {
            System.out.println(comment.getUser());

         }
      }
   }
}
