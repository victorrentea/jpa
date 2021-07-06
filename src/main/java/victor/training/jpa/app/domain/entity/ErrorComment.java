package victor.training.jpa.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class ErrorComment {
   @Id
   @GeneratedValue
   private Long id;
   private String text;

   public ErrorComment(String text) {
      this.text = text;
   }
}
