package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import victor.training.jpa.app.domain.entity.A;
import victor.training.jpa.app.domain.entity.B;
import victor.training.jpa.app.domain.entity.C;
import victor.training.jpa.app.repo.ARepo;
import victor.training.jpa.app.repo.BCNameDto;

import java.util.Arrays;
import java.util.HashSet;

import static java.util.Arrays.asList;

@RequiredArgsConstructor
@Component
public class CostinJoinCascade {
   private final ARepo aRepo;
   public void method() {
      A a = new A();

      aRepo.save(new A().setBList(asList(
          new B("B1").setCList(new HashSet<>(asList(new C("C1"), new C("C2")))),
          new B("B1").setCList(new HashSet<>(asList(new C("C1"), new C("C2"))))
          )));
//      setA.add(a);

      System.out.println(a.hashCode());
      aRepo.save(a);
      System.out.println(a.hashCode());


      System.out.println(aRepo.getAllAWithChildren());

      PageRequest pageRequest = PageRequest.of(2, 10);
      for (BCNameDto objects : aRepo.findAllBC(pageRequest)) {
         System.out.println("A venit: " + objects);
      }
   }
}
