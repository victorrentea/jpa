package victor.training.jpa.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@ToString
//@DynamicUpdate // dark magic
public class ErrorLog  extends BaseEntity{

   @Column(nullable = false)
   private String message;

   private String criticalNote;

   // TODO cascade
   // TODO preserve order (!it matters)
   @OneToMany(cascade = ALL, orphanRemoval = true)
   @JoinColumn
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany
   private Set<ErrorTag> tags = new HashSet<>();


   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
