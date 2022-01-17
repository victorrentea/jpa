package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.entity.Comment;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.repo.PostRepo;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // allow data to remain in DB for later inspectin
public class NPlusOneTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private PostRepo repo;

	@BeforeEach
	public void persistData() {
		repo.deleteAll();
		repo.save(new Post("ORM Mapping")
				.addComment(new Comment("Obvious"))
				.addComment(new Comment("Duh!"))
		);
		repo.save(new Post("JPA Performance")
				.addComment(new Comment("Wow"))
				.addComment(new Comment("Cool"))
				.addComment(new Comment("Great"))
		);

		// emulate separate future transaction
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	public void nPlusOne() {
		List<Post> posts = entityManager.createQuery("SELECT p FROM Post p", Post.class).getResultList();

		int totalChildren = countComments(posts);
		assertThat(totalChildren).isEqualTo(5);
	}

	private int countComments(Collection<Post> posts) {
		log.debug("Start iterating over {} posts: {}", posts.size(), posts);
		int total = 0;
		for (Post post : posts) {
			total += post.getComments().size();
		}
		log.debug("Done counting: {} comments", total);
		return total;
	}



}
