package victor.training.jpa.app.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorTag {
   private Long id;
   private String label;

   public ErrorTag(String label) {
      this.label = label;
   }
}
