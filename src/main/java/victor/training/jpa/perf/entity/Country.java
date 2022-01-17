package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Country {
   @Id
   private Long id;
   private String name;
   private String region;
   private String continent;
   private int population;

   private Country() {
   }

   public Country(Long id, String name) {
      this.id = id;
      this.name = name;
   }

}
