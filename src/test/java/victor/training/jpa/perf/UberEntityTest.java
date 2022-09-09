package victor.training.jpa.perf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UberEntityTest {
    private static final Logger log = LoggerFactory.getLogger(UberEntityTest.class);

    @Autowired
    private EntityManager em;

    private final Country romania = new Country(1L, "Romania");
    private final Country serbia = new Country(2L, "Serbia");
    private final Country bulgaria = new Country(3L, "B");
    private final User testUser = new User(1L,"test");
    private final Scope globalScope = new Scope(1L,"Global");
    private Long uberId;


    @BeforeEach
    final void before() {
        em.persist(romania);
        em.persist(serbia);
        em.persist(bulgaria);
        em.persist(testUser);
        em.persist(globalScope);


        UberEntity uber = new UberEntity()
                .setName("Nume")
                .setIbanCode("iban")
                .setFiscalCountry(romania)
                .setOriginCountry(serbia)
                .setInvoicingCountry(bulgaria)
                .setCreatedBy(testUser)
                .setNationality(romania)
                .setScope(globalScope);
        em.persist(uber);
        uberId = uber.getId();

        // these are roughly equivalent to em.flush(); + em.clear();
        TestTransaction.end(); // COMMIT in DB
        TestTransaction.start();
    }
    @Test
    public void greedyQuery() {
        log.info("Now, loading by id...");
        UberEntity uberEntity = uberEntityRepo.findById(uberId).get();
        log.info("Loaded");

        // 7 JOINURI, 25 de coloane aduse.

        // eu din uber entity am nevoie de fapt de nume si tara de origine

        String astaVreau = uberEntity.getId() + ". "
                           + uberEntity.getName() + " din "
                           + uberEntity.getOriginCountry().getName();
        System.out.println(astaVreau);
    }
    @Test
    public void jqpl() {
        log.info("Now, loading cu JPQL ...");
        UberEntity uberEntity = uberEntityRepo.findByName("Nume");
        // 5 SELECTURI succesive, 1 pentru fiecare relatie @ManyToOne
        String astaVreau = uberEntity.getId() + ". "
                           + uberEntity.getName() + " din "
                           + uberEntity.getOriginCountry().getName();
        System.out.println(astaVreau);
    }
    @Test
    public void maiEficient() {
        log.info("Now, loading cu JPQL ...");
        UberEntity uberEntity = uberEntityRepo.findByName("Nume");
        // 5 SELECTURI succesive, 1 pentru fiecare relatie @ManyToOne
        System.out.println("Cica mi-a intors entity");
        String nameCareNuEraIncaINJavaIncarcat = uberEntity.getOriginCountry().getName();
        String astaVreau = uberEntity.getId() + ". "
                           + uberEntity.getName() + " din "
                           + nameCareNuEraIncaINJavaIncarcat;
        System.out.println(astaVreau);
    }

    @Autowired
    private UberEntityRepo uberEntityRepo;
}

interface UberEntityRepo extends JpaRepository<UberEntity, Long> {
    UberEntity findByName(String name);
}