package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
//@Transactional
//@Rollback(false) // allow data to remain in DB for later inspection
public class NPlusOneTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ParentRepo parentRepo;

	@BeforeEach
	public void persistData() {
		parentRepo.deleteAll();
		parentRepo.save(new Parent("Victor")
				.addChild(new Child("Emma"))
				.addChild(new Child("Vlad"))
		);
		parentRepo.save(new Parent("Peter")
				.addChild(new Child("Maria"))
				.addChild(new Child("Stephan"))
				.addChild(new Child("Paul"))
		);

	}

	@Test
	@Transactional
	public void nPlusOne() {
//		List<Parent> parents = entityManager.createQuery("SELECT p FROM Parent p", Parent.class).getResultList();
		List<Parent> parents = parentRepo.findAll();

		int totalChildren = anotherMethod(parents);
		assertThat(totalChildren).isEqualTo(5);
	}

	private int anotherMethod(Collection<Parent> parents) { // perhaps in a mapper?
		log.debug("Start iterating over {} parents: {}", parents.size(), parents);
		int total = 0;
		for (Parent parent : parents) {
			total += parent.getChildren().size(); // N+1 queries problem
		}
		log.debug("Done counting: {} children", total);
		return total;
	}

	@Test
	void explore() {
		Parent parent = parentRepo.findById(1L).orElseThrow();
		System.out.println(parent.getName());
	}

}
