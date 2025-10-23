package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Country;
import victor.training.jpa.app.entity.CountryRegion;
import victor.training.jpa.app.entity.ScopeEnum;
import victor.training.jpa.app.entity.Uber;
import victor.training.jpa.app.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // don't wipe the data after each test (for debugging)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD) // recreate DB schema before each test
public class UberEntityTest {
  @Autowired
  private EntityManager em;

  private String uberId;

  @BeforeEach
  final void before() {
    Country romania = new Country(1L, "Romania")
        .setRegion(new CountryRegion().setName("EMEA"));
    Country belgium = new Country(2L, "Belgium")
        .setRegion(new CountryRegion().setName("OTHER"));
    Country france = new Country(3L, "France");
    Country serbia = new Country(4L, "Serbia");
    User testUser = new User("test");

    // persist reference data and user
    em.persist(romania);
    em.persist(belgium);
    em.persist(france);
    em.persist(serbia);
    em.persist(testUser);

    Uber uber = new Uber()
        .setName("::uberName::")
        .setStatus(Uber.Status.SUBMITTED)
        .setOriginCountryId(belgium.getId())
        .setFiscalCountry(romania)
        .setInvoicingCountry(france)
        .setNationality(serbia)
        .setScopeEnum(ScopeEnum.GLOBAL) // use enum instead of Spring Data repo
        .setCreatedBy(testUser);

    em.persist(uber);
    uberId = uber.getId();

    TestTransaction.end();
    TestTransaction.start();
  }

  @Test
  public void jpql() {
    log.info("SELECTING a 'very OOP' @Entity with JPQL ...");
    List<Uber> list = em.createQuery("select u from Uber u ", Uber.class).getResultList();
    log.info("Loaded using JPQL (see how many queries are above):\n" + list);
  }

  @Test
  public void findById() {
    log.info("Loading a 'very OOP' @Entity by id...");
    Uber uber = em.find(Uber.class, uberId);
    assertThat(uber).isNotNull();
    log.info("Loaded using findById (inspect the above query):\n" + uber);

    // Use-case: I only loaded UberEntity to get its status
    if (uber.getStatus() == Uber.Status.DRAFT) {
      throw new IllegalArgumentException("Not submitted yet");
    }
    // more logic
  }

  @Test
  public void search() {
    log.info("Searching for a 'very OOP' @Entity...");

    UberSearchCriteria criteria = UberSearchCriteria.builder().name("::uberName::").build();
    List<UberSearchResult> dtos = classicSearch(criteria);

    System.out.println("Results: \n" + dtos.stream().map(UberSearchResult::toString).collect(joining("\n")));
    assertThat(dtos)
        .extracting("id", "name", "originCountry")
        .containsExactly(tuple(uberId, "::uberName::", "Belgium"));

    // TODO [1] Select new Dto
    // TODO [2] Select u.id AS id -> Dto
  }

  private List<UberSearchResult> classicSearch(UberSearchCriteria criteria) {
    String jpql = "SELECT u FROM Uber u WHERE 1 = 1 ";
    // alternative implementation: CriteriaAPI, Criteria+Metamodel, QueryDSL, Spring Specifications
    Map<String, Object> params = new HashMap<>();
    if (criteria.name != null) {
      jpql += " AND u.name = :name ";
      params.put("name", criteria.name);
    }
    if (criteria.status != null) {
      jpql += " AND u.status = :status ";
      params.put("status", criteria.status);
    }
    var query = em.createQuery(jpql, Uber.class);
    for (String key : params.keySet()) {
      query.setParameter(key, params.get(key));
    }
    var results = query.getResultList();

    return results.stream().map(this::toResult).collect(toList());
  }

  private UberSearchResult toResult(Uber entity) {
    String originCountryName = em.createQuery("SELECT c.name FROM Country c WHERE c.id = :countryId", String.class)
        .setParameter("countryId", entity.getOriginCountryId())
        .getSingleResult();
    return new UberSearchResult(
        entity.getId(),
        entity.getName(),
        originCountryName);
  }

  @Builder
  record UberSearchCriteria(String name, Uber.Status status, boolean hasPassport) {
  }

  record UberSearchResult(String id, String name, String originCountry) {
  }
}

