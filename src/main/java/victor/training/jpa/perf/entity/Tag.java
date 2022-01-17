package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tag {
   @Id
   @GeneratedValue
   private Long id;
   private String name;

   public Tag() {}
   public Tag(String name) {
      this.name = name;
   }

}

