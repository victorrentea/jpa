package victor.training.jpa.perf.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.perf.entity.Post;

import java.util.List;
import java.util.Set;

public interface PostRepo extends JpaRepository<Post, Long>, PostRepoCustom {
   @Query("SELECT p FROM Post p WHERE p.title LIKE ?1")
   Page<Post> findByTitleLike(String namePart, Pageable page);

   // TODO delete
   @Query("SELECT p.id FROM Post p WHERE p.title LIKE ?1")
   Page<Long> findIdsByTitleLike(String namePart, Pageable page);

   // TODO delete
   @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user LEFT join FETCH p.comments c LEFT JOIN FETCH c.user where p.id IN (?1)")
   Set<Post> fetchForSearch(List<Long> idList);
}
