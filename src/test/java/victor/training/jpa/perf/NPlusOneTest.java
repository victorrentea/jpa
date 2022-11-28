package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

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
		parentRepo.save(new Parent("Trofim"));

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
		entityManager.clear();// removes everythign from the 1st leve cache

	}

	@Test
	@Transactional
	public void nPlusOne() {
		// only this usecase needs to access the children
		Set<Parent> parents = new HashSet<>(parentRepo.findAllWithChildren(PageRequest.of(20, 0)).getContent());
//		List<Parent> parents = parentRepo.findAll();
		// which are == to each other

		System.out.println(parents);

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
	@Transactional
	void firstLevelCache_persistenceContext_akaTransactionCache() {
		System.out.println(parentRepo.findById(1L).orElseThrow());
		System.out.println(parentRepo.findById(1L).orElseThrow());
		System.out.println(parentRepo.findById(1L).orElseThrow());
		System.out.println(parentRepo.findById(1L).orElseThrow());
		System.out.println(parentRepo.findById(1L).orElseThrow() == parentRepo.findById(1L).orElseThrow());
	}

	@Test
	void explore() {
		Parent parent = parentRepo.findById(1L).orElseThrow();
		System.out.println(parent.getName());
	}
	@Test
	@Transactional
	@Rollback(false) // allow the data in DB
	void parentWithTheNamesOfAllChildren() {
		Set<Parent> parents = new HashSet<>(parentRepo.findAllWithChildren(PageRequest.of(20, 0)).getContent());
		List<String> results = new ArrayList<>();
		for (Parent parent : parents) {
			String childrenNames = parent.getChildren().stream().map(Child::getName).collect(Collectors.joining());
			results.add(parent.getName() + " has " + childrenNames);
		}
		System.out.println(results);
	}
	@Test
	@Transactional
	@Rollback(false) // allow the data in DB
	void parentWithTheNamesOfAllChildrenNATIVE() {
		for (Object[] objects : parentRepo.ourNativeQuery90()) {
			System.out.println("LinE: " + Arrays.toString(objects));
		}
	}

}
