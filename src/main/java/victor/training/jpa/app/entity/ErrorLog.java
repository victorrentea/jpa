package victor.training.jpa.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
// Never @Data on @Entity
// 1) toString causing lazy loading because lombok printed all the collection fields automatically; think of @ToString.Exclude
// 2) Do you really need all the setters ? => get/set on all fields = anemic model; maybe model more DDD-like with
//          less setter, more encapsulation and information hiding inside the Holy Domain Entity
// 3) hashCode includes the @Id which CHANGES at .save() -> may lead to HashSet<Ent> corruption
   // in my pref, I do NOT implement any hashCode/equals

// SW: use a Factory to guarantee that any @ENtity has an ID assigned from start, and that never changes.
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;

   @Column(nullable = false)
   private String message;

   // TODO cascade
   // TODO preserve order (!it matters)
   @OneToMany
   @JoinColumn(name = "ERROR_LOG_ID")
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany
   private Set<ErrorTag> tags = new HashSet<>();


   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
