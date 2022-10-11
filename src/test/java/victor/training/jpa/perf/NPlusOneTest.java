package victor.training.jpa.perf;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.EntityManager;
import java.lang.management.MemoryType;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // allow data to remain in DB for later inspection
public class NPlusOneTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ParentRepo parentRepo;

	@BeforeEach
	public void persistData() {
		parentRepo.deleteAll();
		parentRepo.save(new Parent("Trofim"));

//		Child child = new Child("Emma");
//		Parent p = new Parent("Victor");
//
//		child.setParent(p);

		parentRepo.save(new Parent("Victor")
				.addChild(new Child("Emma"))
				.addChild(new Child("Vlad"))
		);
		parentRepo.save(new Parent("Peter")
				.addChild(new Child("Maria"))
				.addChild(new Child("Stephan"))
				.addChild(new Child("Paul"))
		);
		entityManager.flush();
		entityManager.clear();// clears the 1st level cache (Persisten Context)
	}

	@Test
	@Transactional
	public void nPlusOne() {
		List<Parent> parents = parentRepo.findAll();
//		List<Parent> parents = parentRepo.findParentsWithChildren();

		int totalChildren = anotherMethod(parents);
		assertThat(totalChildren).isEqualTo(5);
	}

	private int anotherMethod(Collection<Parent> parents) {
		log.debug("Start iterating over {} parents: {}", parents.size(), parents);
		int total = 0;
		for (Parent parent : parents) {
			System.out.println("How is it possible that a call to .size() launches a qury ??! " +
							   "isn't that List<Children> a list? NO: " + parent.getChildren().getClass());
			total += parent.getChildren().size(); // launches + 1 query for each parent = N
		}
		log.debug("Done counting: {} children", total);
		return total;
	}

//	@TransactionalEventListener(value = NewMessageToSendEvent.class, phase = TransactionPhase.AFTER_COMMIT)
//	@Scheduled(fixedRate = 500)
//	public void method() {
//
//	}
}
