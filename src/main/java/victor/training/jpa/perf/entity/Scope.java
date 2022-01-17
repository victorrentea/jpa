package victor.training.jpa.perf.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Scope {
   @Id
   private Long id;
   private String name;

   private Scope() {
   }

   public Scope(Long id, String name) {
      this.id = id;
      this.name = name;
   }
}
