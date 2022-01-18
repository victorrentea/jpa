package victor.training.jpa.perf.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.perf.PostDto;
import victor.training.jpa.perf.entity.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface PostRepo extends JpaRepository<Post, Long>, PostRepoCustom, JpaSpecificationExecutor<Post> {
   @Query("SELECT p FROM Post p WHERE p.title LIKE ?1")
   Page<Post> findByTitleLike(String namePart, Pageable page);

   @Query("SELECT p.id FROM Post p WHERE p.title LIKE ?1") // driving query
   Page<Long> findIdByTitleLike(String namePart, Pageable page);


   @Query("SELECT p " +
          "FROM Post p " +
          "LEFT JOIN FETCH p.author " +
          "LEFT JOIN FETCH p.comments " +
          "WHERE p.id IN (?1)")
   Set<Post> fetchManyWithChildren(List<Long> postIds);


   @Query("SELECT new victor.training.jpa.perf.PostDto(p.id, p.title, p.author.username)" +
          " FROM Post p WHERE p.title LIKE ?1")
   Page<PostDto> findDtosByTitleLike(String namePart, Pageable page);


   @EntityGraph(value = "Post.forCommenters")
   @Query("SELECT p FROM Post p WHERE p.publishDate > ?1")
   Set<Post> findPostsAfter(LocalDate when);

   @Query("FROM Post")
   Stream<Post> streamAll();

   @Query("SELECT DISTINCT p FROM Post p" +
          " LEFT JOIN FETCH p.comments c" +
          " LEFT JOIN FETCH c.tags")
   Collection<Post> fetchWithComments();
}
