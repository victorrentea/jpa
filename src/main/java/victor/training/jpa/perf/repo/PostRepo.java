package victor.training.jpa.perf.repo;

import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.perf.entity.Post;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long>, PostRepoCustom {
   @Query("SELECT p FROM Post p WHERE p.title LIKE ?1")
   Page<Post> findByTitleLike(String namePart, Pageable page);
}
