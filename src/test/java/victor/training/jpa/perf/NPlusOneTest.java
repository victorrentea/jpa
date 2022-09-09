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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class NPlusOneTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ParentRepo parentRepo;
	private Long peterId;

	@BeforeEach
	public void persistData() {
		parentRepo.deleteAll();
		parentRepo.save(new Parent("Victor")
				.addChild(new Child("Emma"))
				.addChild(new Child("Vlad"))
		);
		peterId = parentRepo.save(new Parent("Peter")
				.addChild(new Child("Maria"))
				.addChild(new Child("Stephan"))
				.addChild(new Child("Paul"))
		).getId();

		entityManager.flush();
		entityManager.clear();
	}

//	@Test
//	@Transactional
//	@Rollback(false)
//	public void findById() {
//		Parent petru = parentRepo.findById(peterId).orElseThrow(); // mereg cu JOIN daca pui fetch=EAGER
//
//		System.out.println(petru.getChildren());
//	}
	@Test
	@Transactional
	@Rollback(false)
	public void nPlusOne() {
//		List<Parent> parents = entityManager.createQuery("SELECT p FROM Parent p", Parent.class).getResultList();
		List<Parent> parents = parentRepo.findAll();
//		Set<Parent> parents = parentRepo.findAllWithChildren();

		int totalChildren = countChildren(parents);
		assertThat(totalChildren).isEqualTo(5);
	}

	private int countChildren(Collection<Parent> parents) {
		log.debug("Start iterating over {} parents: {}", parents.size(), parents);
		int total = 0;
		for (Parent parent : parents) {
			total += parent.getChildren().size();
		}
		log.debug("Done counting: {} children", total);
		// Session(Hib) = PerssitenceCOntext(JPA) = @Transactional (Spring) contine 1st level cache
		return total;
	}

}
