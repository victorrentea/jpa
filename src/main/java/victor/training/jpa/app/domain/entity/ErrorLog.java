package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@ToString
//@NamedQueries(@NamedQuery(name="ceva1", "SELECT "))
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;

   @Column(nullable = false)
   private String message;

   private String incaCeva;

//   private List<ErrorComment> comments = new ArrayList<>();

//   private Set<ErrorTag> tags = new HashSet<>();

   public ErrorLog() {
   }
   public ErrorLog(String message) {
      this.message = message;
   }

}
