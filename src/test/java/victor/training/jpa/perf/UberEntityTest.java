package victor.training.jpa.perf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
                .setOriginCountry(romania)
                .setInvoicingCountry(romania)
                .setCreatedBy(testUser)
                .setNationality(romania)
                .setScope(globalScope);
        em.persist(uber);

        TestTransaction.end();
        TestTransaction.start();

        log.info("Now, loading by id...");
        Object[] uberEntity = uberRepo.getStrictlyNecessary(uber.getId()).get(0);
        log.info("Loaded");
        log.info("Tre sa printez in UI: id, name si originCountry.name, asdsa si , is, is, si");


        // TODO fetch only the necessary data
        // TODO change link types?
        System.out.println(Arrays.toString(uberEntity));
    }

    @Autowired
    private UberRepo uberRepo;
}


interface UberRepo extends JpaRepository<UberEntity, Long> {

    @Query("SELECT u.id, u.name, u.originCountry.name FROM UberEntity u WHERE u.id = ?1")
    List<Object[]> getStrictlyNecessary(Long id);
}


// JSON
class UberEntitySearchResult {
    public final long id;
    public final String name;
    public final String originCountryName;

    public UberEntitySearchResult(long id, String name, String originCountryName) {
        this.id = id;
        this.name = name;
        this.originCountryName = originCountryName;
    }
}