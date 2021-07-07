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

   private Integer i = -1;

   @Version
   private Long version;

   @OneToMany(cascade = ALL, orphanRemoval = true) // pt copiii a caror existenta e conditionata de a parintelui
   @JoinColumn(name = "ERROR_ID")
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany
   private Set<ErrorTag> tags = new HashSet<>();
   @ManyToOne
   private ErrorTag mainTag;

   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
