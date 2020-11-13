package victor.training.jpa.perf;

import lombok.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
                .setFiscalCountryId(romania.getId()) // IN THE DB SCHEMA PLEASE DO KEEP THE FK. the hibernate generated schema will now NOT have the FK by default
                .setOriginCountry(romania)
                .setInvoicingCountry(romania)
                .setCreatedBy(testUser)
                .setNationality(romania)
                .setScope(globalScope);
        em.persist(uber);

        TestTransaction.end();
        TestTransaction.start();

        log.info("Now, loading by id...");

//        UberEntity uberEntity = em.find(UberEntity.class, uber.getId());
        UberEntitySearchResult uberEntity = em.createQuery("SELECT new victor.training.jpa.perf.UberEntitySearchResult(u.id, u.name, fc) " +
                                                           "FROM UberEntity u LEFT JOIN Country fc ON fc.id = u.fiscalCountryId WHERE u.id =:id", UberEntitySearchResult.class)
            .setParameter("id", uber.getId()).getSingleResult();
        // suppose in UI search results or exports you really need the label of the country.

        // A : export  keep all the countryies in mem / @Cacheable or a simple HashMap loaded for that request/

        // TODO B) search: select new bla.bla.MySearchResult(country)

        //uberEntity.getOriginCountry().getName() // DO I EVER EVER DO THIS IN MY APP !? - ask yourselves very honestly
        log.info("Loaded");
        // TODO fetch only the necessary data
        // TODO change link types?
        System.out.println(uberEntity.toString());
    }
}

@Value
class UberEntitySearchResult {
    long id;
    String name;
//    String fiscalCountryName;
    Country fiscalCountry;
}