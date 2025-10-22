package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Child;
import victor.training.jpa.app.entity.Country;
import victor.training.jpa.app.entity.Parent;
import victor.training.jpa.app.entity.ParentSearchView;
import victor.training.jpa.app.web.ParentDto;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // COMMIT at the end of each @Test, to be able to look in the DB contents
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD) // nuke Spring + re-init DB with Hibernate
public class NPlusOneTest {
  @Autowired
  EntityManager entityManager;

  @BeforeEach
  void persistData() {
    // cleanup existing data (children depend on parents)
    entityManager.createQuery("delete from Child").executeUpdate();
    entityManager.createQuery("delete from Parent").executeUpdate();
    entityManager.createQuery("delete from Country").executeUpdate();

    Country romania = new Country(1L, "Romania");
    Country moldavia = new Country(2L, "Moldavia");
    entityManager.persist(romania);
    entityManager.persist(moldavia);

    Parent victor = new Parent("Victor")
        .setCountry(romania)
        .setAge(36)
        .addChild(new Child("Emma"))
        .addChild(new Child("Vlad"));
    entityManager.persist(victor);

    Parent peter = new Parent("Peter")
        .setAge(41)
        .setCountry(romania)
        .addChild(new Child("Maria"))
        .addChild(new Child("Paul"))
        .addChild(new Child("Stephan"));
    entityManager.persist(peter);

    Parent trofim = new Parent("Trofim") // bachelor, no children
        .setCountry(moldavia)
        .setAge(42);
    entityManager.persist(trofim);

    TestTransaction.end(); // force a COMMIT
    TestTransaction.start();
  }

  // This is what is displayed in a UI grid:
  private static void assertResults(Collection<?> results) {
    assertThat(results)
        .extracting("name", "childrenNames")
        .containsExactlyInAnyOrder(
            tuple("Trofim", ""),
            tuple("Victor", "Emma,Vlad"),
            tuple("Peter", "Maria,Paul,Stephan"));
  }

  // ======================= SELECT full @Entity =============================
  @Test
  public void selectFullEntity() {
    System.out.println("--- daca vezi linia asta, app a reusit sa porneasca corect");
//    List<Parent> parents = entityManager.createQuery(
//            "    SELECT p" +
//            "FROM Parent p" +
//            "    LEFT JOIN FETCH p.children" +
//            "    LEFT JOIN FETCH p.country", Parent.class)
    List<Parent> parents = entityManager.createNamedQuery("Parent.fetchWithChildren", Parent.class)
        .getResultList();
    log.info("Loaded {} parents: {}", parents.size(), parents);

    List<ParentDto> results = toSearchResults(parents);
    assertResults(results);
  }

  private List<ParentDto> toSearchResults(Collection<Parent> parents) { // eg, in a Mapper
    log.debug("Converting-->Dto START");
    List<ParentDto> results = parents.stream().map(ParentDto::fromEntity).toList();
    log.debug("Converting-->Dto DONE");
    return results;
  }

  // ======================= Using EntityManager with JPQL/native to return DTOs ==================
  @Test
  public void nativeQuery() {
    System.out.println("--- daca vezi linia asta, app a reusit sa porneasca corect");

    var results = entityManager.createNamedQuery("mirela", ParentDto.class)
        .getResultList();
    assertResults(results);
  }

  // ======================= Simulated @Subselect via JPQL ==================
  @Test
  public void subselect() {
    List<Parent> parents = entityManager.createQuery("select p from Parent p", Parent.class)
        .getResultList();
    List<ParentDto> results = toSearchResults(parents);
    assertResults(results);
  }

  // ======================= Simulated DB VIEW via JPQL =============================
  @Test
  public void view() {
    List<ParentSearchView> parents = entityManager.createQuery("""
        select psv 
        from ParentSearchView psv
        INNER JOIN Parent p ON psv.id=p.id
        WHERE p.age<45
""", ParentSearchView.class)
        .getResultList();
    List<ParentDto> results = parents.stream()
        .map(psv -> new ParentDto(psv.getId(), psv.getName(), psv.getChildrenNames()))
        .toList();
    assertResults(results);
  }

}

