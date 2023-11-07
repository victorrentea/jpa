package victor.training.jpa.app.entity;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class ErrorTag {
   @Id
   @GeneratedValue
   private Long id;
   private String label;

   public ErrorTag(String label) {
      this.label = label;
   }
}
