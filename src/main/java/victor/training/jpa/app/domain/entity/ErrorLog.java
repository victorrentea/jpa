package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REFRESH;

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

   private LocalDateTime createdAt;

//   private List<ErrorComment> comments = new ArrayList<>();

   @OneToMany(cascade = ALL, orphanRemoval = true) // Vlad si Thorben te alearga la faza asta (fetch = FetchType.EAGER) // are send NUMAI SI NUMAI pentru Aggregate (DDD) - > private Entity
   @JoinColumn
   private Set<ErrorTag> tags = new HashSet<>();

   @Version
   private LocalDateTime lastChange;

   private ErrorLog() {
   }
   public ErrorLog(String message) {
      setMessage(message);
   }


   public void setMessage(String message) {
      this.message = Objects.requireNonNull(message.trim());
   }
}
