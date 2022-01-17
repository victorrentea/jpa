package victor.training.jpa.perf.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.perf.entity.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface PostRepo extends JpaRepository<Post, Long>, PostRepoCustom, JpaSpecificationExecutor<Post> {
   @Query("SELECT p FROM Post p WHERE p.title LIKE ?1")
   Page<Post> findByTitleLike(String namePart, Pageable page);



   // TODO @EntityGraph
   @Query("SELECT p FROM Post p WHERE p.publishDate > ?1")
   Set<Post> findPostsAfter(LocalDate when);

   @Query("FROM Post")
   Stream<Post> streamAll();
}
