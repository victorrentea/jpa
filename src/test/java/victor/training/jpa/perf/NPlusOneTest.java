package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // allow data to remain in DB for later inspectin
public class NPlusOneTest {

	@Autowired
	private EntityManager em;
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void nPlusOne() {
		List<Parent> parents = em.createQuery("SELECT  DISTINCT  p FROM Parent p"
//														  + " LEFT JOIN FETCH p.children "
				, Parent.class)
			.setFirstResult(0).setMaxResults(4) // pag 1 max 4 parinti >>> LIMIT OFFSET
			.getResultList() // daca ai in baza 10M de randuri >>
			;

		List allChildren = em.createQuery("SELECT c FROM Child c WHERE c.id IN (:parentIds)")
			.setParameter("parentIds", parents.stream().map(Parent::getId).collect(Collectors.toList()))
			.getResultList();

		System.out.println(allChildren);

		// paginare pe parinti


//			.getResultStream().collect(Collectors.toSet());
//		repo.findAll();

		int totalChildren = anotherMethod(parents);
		assertThat(totalChildren).isEqualTo(5);
	}


	private int anotherMethod(Collection<Parent> parents) {
		log.debug("Start iterating over {} parents: {}", parents.size(), parents);
		int total = 0;
		for (Parent parent : parents) {
			total += parent.getChildren().size();
		}
		log.debug("Done counting: {} children", total);
		return total;
	}

}
