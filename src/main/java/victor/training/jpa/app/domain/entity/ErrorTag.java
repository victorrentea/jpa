package victor.training.jpa.app.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
