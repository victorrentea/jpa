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

import javax.persistence.*;
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
        UberEntitySearchResult uberEntity = uberRepo.getStrictlyNecessary(uber.getId()).get(0);
        log.info("Loaded");
        log.info("Tre sa printez in UI: id, name si originCountry.name, asdsa si , is, is, si");


        // TODO fetch only the necessary data
        // TODO change link types?
        System.out.println(uberEntity);
    }

    @Autowired
    private UberRepo uberRepo;
}


interface UberRepo extends JpaRepository<UberEntity, Long> {

    @Query("SELECT new victor.training.jpa.perf.UberEntitySearchResult(" +
            "u.id, u.name, u.originCountry.name, u.firstName) FROM UberEntity u WHERE u.id = ?1")
    List<UberEntitySearchResult> getStrictlyNecessary(Long id);

    // cu view arata asa :
    // SELECT us from UberEntitySearchView us JOIN UberEntity u ON u.id=us.is WHERE u. and u.blabla and u.
}


// JSON
class UberEntitySearchResult {
    public final long id;
    public final String name;
    public final String originCountryName;
    public final String firstName;

    public UberEntitySearchResult(long id, String name, String originCountryName, String firstName) {
        this.id = id;
        this.name = name;
        this.originCountryName = originCountryName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "UberEntitySearchResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originCountryName='" + originCountryName + '\'' +
                '}';
    }
}
// CREATE OR REPLACE [materialized:(] VIEW UBER_SEARCH_VIEW AS (SELECT ,,,, FROM )
@Entity
@Table(name="UBER_SEARCH_VIEW")
class UberEntitySearchView {
    @Id
    public Long id;
    public String name;
    @Column(name = "ORIGIN_COUNTRY_NAME")
    public String originCountryName;
    public String firstName;
}