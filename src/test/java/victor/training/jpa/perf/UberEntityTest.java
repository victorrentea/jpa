package victor.training.jpa.perf;

import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;

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
    private UberEntity uber;

    @BeforeEach
    final void before() {
        em.persist(romania);
        em.persist(testUser);
        em.persist(globalScope);


        uber = new UberEntity()
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
    }

    @Test
    public void fbi() {
        log.info("Now, find by Id...");
        UberEntity uberEntity = uberRepo.findById(uber.getId()).orElseThrow(); // .find
        log.info("Loaded");
        // SURPISE: if you findById an @ENtity with many links to static data
        // (eg @ManyToOne Country country;) ->
        // JPA will pre-load all those links with JOINs in case you do a findById
        System.out.println(uberEntity.getStatus());
    }
    @Test
    public void query() {
        log.info("Now, JPQL query to load the data...");
        List<UberEntity> list = uberRepo.findAll();
//        uberRepo.myOwnQuery();
        // or any @Qyery() JQPL SELECT
        // SURPISE: if you findById an @ENtity with many links to static data
        // (eg @ManyToOne Country country;) ->
        // JPA will pre-load all those links with +1 select / @ManyToOne
        // for example if loading a page of 2o items each item with 4 @ManyToOne, you might end up running 1+20x4 = 81 queries
        log.info("Loaded");
        log.info("List " + list);
    }

    @Autowired
    private UberRepo uberRepo;
}
