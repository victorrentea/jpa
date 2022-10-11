package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class MyRestEndpoint implements CommandLineRunner {

    @Autowired
    ParentRepo parentRepo;
    @GetMapping
    public int method() {
        List<Parent> parents = parentRepo.findAll();

        int totalChildren = anotherMethod(parents);
        return totalChildren;
    }
    @GetMapping("never")
    public Parent theLackOfAnyArchitecture() { // design mistake! clients coupled to your domain model > you can't change it later
        Parent entity = parentRepo.findAll().get(0);
        log.debug("END OF CONTROLLER");
//        restTemplate call 1s
        return entity;
    }

    private int anotherMethod(Collection<Parent> parents) {
        log.debug("Start iterating over {} parents: {}", parents.size(), parents);
        int total = 0;
        for (Parent parent : parents) {
            System.out.println("How is it possible that a call to .size() launches a qury ??! " +
                               "isn't that List<Children> a list? NO: " + parent.getChildren().getClass());
            total += parent.getChildren().size(); // launches + 1 query for each parent = N
        }
        log.debug("Done counting: {} children", total);
        return total;
    }

    @Override
    public void run(String... args) throws Exception {
        parentRepo.deleteAll();

        parentRepo.save(new Parent("Victor")
                .addChild(new Child("Emma"))
                .addChild(new Child("Vlad"))
        );
        parentRepo.save(new Parent("Trofim"));

        parentRepo.save(new Parent("Peter")
                .addChild(new Child("Maria"))
                .addChild(new Child("Stephan"))
                .addChild(new Child("Paul"))
        );
    }
}
