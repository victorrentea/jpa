package victor.training.jpa.perf;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    @Test
    public void greedyQuery() {
        em.persist(romania);
        em.persist(testUser);
        em.persist(globalScope);


        UberEntity uber = new UberEntity()
                .setName("SomeName")
                .setFiscalCountry(romania)
                .setOriginCountry(romania)
                .setInvoicingCountry(romania)
                .setCreatedBy(testUser)
                .setNationality(romania)
                .setScope(globalScope);
        em.persist(uber);

        // these are roughly equivalent to em.flush(); + em.clear();
        TestTransaction.end();
        TestTransaction.start();

        log.info("Now, loading by id...");
        // find by id => 5 JOINS
//        UberEntity uberEntity = repo.findById(UberEntity.class, uber.getId());

        // find by id => 4 SELECTs (repeated)
//        UberEntity uberEntity = uberEntityRepo.findAll().get(0);

//        UberProjection dtoJson = uberEntityRepo.findAllProjected().get(0);
//        System.out.println("Would I see this(runtime call)? or will Spring fail to start ?");

//        TypedQuery<UberProjection> q = em.createQuery(
//                "SELECT new victor.training.jpa.perf.UberProjection" +
//                      "(u.id, u.nameu.originCountry.name) " +
//                      "FROM UberEntity u", UberProjection.class);
//        UberProjection dtoJson = q.getResultList().get(0);


        UberProjectionInterface2 dtoJson = uberEntityRepo.findAllProjectedInterface3().get(0);

        log.info("Loaded");
        // TODO fetch only the necessary data
        // TODO change link types?
        System.out.println(dtoJson.getId() + " | " +
                           dtoJson.getName() + " | "
                 + dtoJson.getOriginCountry().getName()
//                           + dtoJson.getOriginCountryName()
        ) ;
    }

    @Autowired
    private UberEntityRepo uberEntityRepo;
}
@Data

class UberProjection {
    private final Long id;
    private final String name;
    private final String originCountryName;
}
interface UberProjectionInterface {
    @JsonProperty("forMqAtttrName") // Rabbit
    Long getId();
    String getName();
    String getOriginCountryName();
}

interface UberEntityRepo extends JpaRepository<UberEntity, Long> {
//    @Query("select u  FROM UberEntity u WHERE u.name=?1")
    UberEntity findByName(String name); // ==> JPQL ("Select u  FROM UberEntity u WHERE u.name=?1" --> SQL

    @Query("SELECT new victor.training.jpa.perf.UberProjection" +
           "(u.id, u.name, u.originCountry.name) " +
           "FROM UberEntity u")
    List<UberProjection> findAllProjected(); // ==> JPQL ("Select u  FROM UberEntity u WHERE u.name=?1" --> SQL

    @Query("SELECT u.id AS id, u.name AS name," +
           " u.originCountry.name AS originCountryName " +
           "FROM UberEntity u")
    List<UberProjectionInterface> findAllProjectedInterface(); // ==> JPQL ("Select u  FROM UberEntity u WHERE u.name=?1" --> SQL


    // even more geek, do not abuse.
    @Query("SELECT u " +
           "FROM UberEntity u")
    List<UberProjectionInterface2> findAllProjectedInterface3(); // ==> JPQL ("Select u  FROM UberEntity u WHERE u.name=?1" --> SQL

    @Modifying // put this
    @Query("UPDATE UberEntity u SET u.name = ?2 WHERE u.id=?1")

    // why? had a performance issue? UberEntity is large. Avoid fetching too much data over network.
    // what's wrong with this ?
    // ?design: we can bypass some constraints to change that field
    void setOneFieldOnly(Long id, String name);
}