package victor.training.jpa.app.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
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
