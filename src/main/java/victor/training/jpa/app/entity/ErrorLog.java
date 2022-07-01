package victor.training.jpa.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;

@Getter
@Setter
@Entity
@ToString
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;
   @Version
   private Long version;

   @Column(nullable = false)
   private String message;

   // TODO cascade
   // TODO preserve order (!it matters)
   @OneToMany(cascade = ALL, orphanRemoval = true)
   @JoinColumn
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany(cascade = ALL)
   private Set<ErrorTag> tags = new HashSet<>();


   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
