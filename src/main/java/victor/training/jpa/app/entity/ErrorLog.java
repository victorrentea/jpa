package victor.training.jpa.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@ToString
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;

   @Column(nullable = false)
   private String message;

   // TODO cascade
   // TODO preserve order (!it matters)
   @OneToMany(cascade = ALL, orphanRemoval = true)
   // cascade=MERGE would implement UPDATE, INSERT, and UNLINK a new child
   // orphanRemoval=true makes the child be DELETED when unlinked from a parent
   @JoinColumn(name = "ERROR_LOG_ID")
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany
   private Set<ErrorTag> tags = new HashSet<>();

   @Column(updatable = false) // Nice !
   private String creationUser;

   private String lastModifiedBy;


   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
