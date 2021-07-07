package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import victor.training.jpa.app.domain.entity.A;
import victor.training.jpa.app.repo.ARepo;

@RequiredArgsConstructor
@Component
public class CostinJoinCascade {
   private final ARepo aRepo;
   public void method() {
      A a = new A();

//      setA.add(a);

      System.out.println(a.hashCode());
      aRepo.save(a);
      System.out.println(a.hashCode());


      System.out.println(aRepo.getAllAWithChildren());
   }
}
