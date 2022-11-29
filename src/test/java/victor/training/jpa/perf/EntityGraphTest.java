package victor.training.jpa.perf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest

public class EntityGraphTest {

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private ParentRepo parentRepo;

  @BeforeEach
  public void persistData() {
    parentRepo.deleteAll();
    parentRepo.save(new Parent("Trofim"));

    parentRepo.save(new Parent("Victor")
            .addChild(new Child("Emma"))
            .addChild(new Child("Vlad"))
    );
    parentRepo.save(new Parent("Peter")
            .addChild(new Child("Maria"))
            .addChild(new Child("Stephan"))
            .addChild(new Child("Paul"))
    );

  }

  @Test
  void parentsWithChildrenNames() {
    // I don't want to load children' ages,
    List<Parent> parents = parentRepo.findParentsByGraph();
    System.out.println("PARENTS LOADED --- (no lazy loading allowed after this line)");
    for (Parent parent : parents) {
      String childNames = parent.getChildren().stream().map(Child::getName).collect(Collectors.joining(", "));
      System.out.println(parent.getId() + "|" + parent.getName() + "|" + childNames);
    }
  }

}
