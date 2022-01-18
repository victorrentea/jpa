package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.entity.Comment;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.repo.PostRepo;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // allow data to remain in DB for later inspection. useful for demos, but avoid pushing it on your project `remote`: leaves state in DB
public class NPlusOneTest {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private PostRepo postRepo;

	@BeforeEach
	public void persistData() {
		postRepo.deleteAll();
		postRepo.save(new Post("ORM Mapping")
				.addComment(new Comment("Obvious"))
				.addComment(new Comment("Duh!"))
		);
		postRepo.save(new Post("ORM Mapping#2")
				.addComment(new Comment("Obvious"))
				.addComment(new Comment("Duh!"))
		);
		postRepo.save(new Post("JPA Performance")
				.addComment(new Comment("Wow"))
				.addComment(new Comment("Cool"))
				.addComment(new Comment("Great"))
		);

		TestTransaction.end();
		TestTransaction.start();
	}

	@Test
	public void nPlusOne() {
//		List<Post> posts = postRepo.findAll();
//		List<Post> posts = entityManager.createQuery("SELECT p FROM Post p " +
//																	" LEFT JOIN FETCH p.comments", Post.class)
//			.getResultList();

		Set<Post> posts = postRepo.fetchWithComments();

		int totalChildren = countComments(posts);
		assertThat(totalChildren).isEqualTo(7);
		// TODO explain @BatchSize
	}


	private int countComments(Collection<Post> posts) {
		log.debug("Start iterating over {} posts: {}", posts.size(), posts);
		int total = 0;
		for (Post post : posts) {
//			System.out.println("What set impl do i Have : " + post.getComments().getClass());
			total += post.getComments().size();//lazy loading only works within an open Transaction
		}
		log.debug("Done counting: {} comments", total);
		return total;
	}



}
