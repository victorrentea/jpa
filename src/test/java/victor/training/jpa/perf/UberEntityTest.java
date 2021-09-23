package victor.training.jpa.perf;

import lombok.Value;
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
import javax.persistence.TypedQuery;
import java.util.List;

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
//        repo.findById
        UberEntity uberEntity = em.find(UberEntity.class, uber.getId());
//        uberEntity.getOriginCountry().getId();
        log.info("Loaded");
        // TODO change link types?
        // TODO fetch only the necessary data
        System.out.println(uberEntity.toString());

        // Din REST api aduci countryId=23 in FE tii o mapa intre countryid-nume
        // daca vrei sa exporti in CSV/XLS NUME: preincarci in Java intr-un Map<Lng,String> countryNames si exporti facand map.get(id)


        List<UberEntityDto> dtos = em.createQuery(
            "SELECT new victor.training.jpa.perf.UberEntityDto(u.id, u.name, u.cnp, c.name) " +
            " FROM UberEntity u " +
            " JOIN Country c ON c.id = u.originCountryId",UberEntityDto.class).getResultList();

        System.out.println(dtos);
        // sa pp ca faci un search dupa  Uber Entity
        // |id|name|cnp|OriginCOuntryName|
        // |  |   |
    }
}
@Value
class UberEntityDto {
    Long id;
    String name;
    String cnp;
    String originCountry;
}