package victor.training.jpa.perf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UberEntityTest {
    private static final Logger log = LoggerFactory.getLogger(UberEntityTest.class);

    @Autowired
    private EntityManager em;

    private final Country romania = new Country(1L, "Romania");
    private final User testUser = new User(1L,"test");
    private final Scope globalScope = new Scope(1L,"Global");

    @Test
    public void greedyQuery() {
        em.persist(romania);
        em.persist(testUser);
        em.persist(globalScope);


        UberEntity uber = new UberEntity()
                .setFiscalCountry(romania)
                .setOriginCountryId(romania.getId())
                .setInvoicingCountry(romania)
                .setCreatedBy(testUser)
                .setNationality(romania)
                .setScope(globalScope);
        em.persist(uber);

        // these are roughly equivalent to em.flush(); + em.clear();
        TestTransaction.end();
        TestTransaction.start();

        log.info("Now, loading by id...");
        UberEntity uberEntity = em.find(UberEntity.class, uber.getId());
        log.info("Loaded");
        // imagine a search screen result table
        // |id| name | origin country name | CNP |
        // TODO fetch only the necessary data: name s
        // TODO change link types?
        System.out.println(uberEntity.toString());
    }
}
