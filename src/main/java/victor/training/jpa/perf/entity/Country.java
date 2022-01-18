package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY;

@Entity
@Getter
@Setter
 @Cache(usage = READ_ONLY)
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
