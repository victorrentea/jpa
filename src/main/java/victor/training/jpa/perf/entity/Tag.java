package victor.training.jpa.perf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tag {
   @Id
//   private String id = UUID.randomUUID().toString();
   @GeneratedValue
   private Long id;
   private String name;

   public Tag() {}
   public Tag(String name) {
      this.name = name;
   }

}

