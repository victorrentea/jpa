package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.entity.Country;
import victor.training.jpa.perf.entity.Scope;
import victor.training.jpa.perf.entity.UberEntity;
import victor.training.jpa.perf.entity.User;
import victor.training.jpa.perf.repo.UberRepo;

import javax.persistence.EntityManager;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UberEntityTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UberRepo uberRepo;

    private Country romania = new Country(1L, "Romania");
    private User testUser = new User("test");
    private Scope globalScope = new Scope(1L, "Global");

    @Test
    public void greedyQuery() {
        entityManager.persist(romania);
        entityManager.persist(testUser);
        entityManager.persist(globalScope);

        UberEntity uber = new UberEntity()
                .setFiscalCountry(romania)
                .setOriginCountryId(romania.getId()) // One less JOIN or -1 SELECT
                .setInvoicingCountry(romania)
                .setCreatedBy(testUser)
                .setNationality(romania)
                .setScope(globalScope);
        entityManager.persist(uber);

        TestTransaction.end();
        TestTransaction.start();

        log.info("Now, loading by id...");
//        UberEntity uberEntity = entityManager.find(UberEntity.class, uber.getId());
//        UberEntity uberEntity = uberRepo.findAll().get(0);
        UberDto dto =  uberRepo.loadForUI(uber.getId());
        log.info("Loaded");
        // TODO fetch only the necessary data to display in UI: id, name, originCountryName
        // TODO change link types?
        System.out.println(dto.toString());
    }
}

