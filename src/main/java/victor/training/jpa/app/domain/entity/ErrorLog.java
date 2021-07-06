package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;

   @Column(nullable = false)
   private String message;

   @JoinColumn
   @OneToMany(cascade = ALL, orphanRemoval = true)
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany
   private Set<ErrorTag> tags = new HashSet<>();

   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
