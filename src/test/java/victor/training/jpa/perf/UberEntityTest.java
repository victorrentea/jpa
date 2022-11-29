package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UberEntityTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private UberEntityRepo repo;
    @Autowired
    private RegionRepo regionRepo;
    @Autowired
    private CountryRepo countryRepo;

    private final Country romania = new Country(1L, "Romania");
    private final Country serbia = new Country(2L, "Serbia");
    private final Country belgium = new Country(3L, "Belgium");
    private final Country netherlands = new Country(4L, "Netherlands");
    private final User testUser = new User(1L,"test");
    private final Scope globalScope = new Scope(1L,"Global");

    @Test
    public void greedyQuery() {
        em.persist(romania);
        em.persist(romania);
        em.persist(testUser);
        em.persist(globalScope);


        UberEntity uber = repo.save(new UberEntity()
                .setFiscalCountry(romania)
                .setOriginCountry(serbia)
                .setInvoicingCountry(belgium)
                .setNationality(netherlands)
                .setCreatedBy(testUser)
                .setScope(globalScope));
       repo.save(uber);

        // these are roughly equivalent to em.flush(); + em.clear();
        TestTransaction.end();
        TestTransaction.start();

        log.info("Now, loading by id...");
        UberEntity uberEntity = em.find(UberEntity.class, uber.getId());
        log.info("Loaded");
        // TODO fetch only the necessary data
        // TODO change link types?
        System.out.println(uberEntity.toString());
    }
}
