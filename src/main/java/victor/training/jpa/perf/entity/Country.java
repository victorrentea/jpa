package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
// @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Country {
   @Id
   private Long id;
   private String name;
   private String region;
   private String continent;
   private Integer population;

   protected Country() {
   }

   public Country(Long id, String name) {
      this.id = id;
      this.name = name;
   }

}