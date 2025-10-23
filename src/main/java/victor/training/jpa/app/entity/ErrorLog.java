package victor.training.jpa.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@ToString
@DynamicUpdate
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;

   @Column(nullable = false)
   private String message;
   private String descr;

   @Column//(updatable = false)// draga JPA, daca vreun dev confuz incearca, ignora
   private String createdBy;

   // TODO preserve order (!it matters)
   @OneToMany(cascade = ALL,
       fetch = FetchType.EAGER,
       orphanRemoval = true // OMOARA copii scosi din lista, care ar fi ramas cu parentId=NULL
   )
   @JoinColumn(name = "ERROR_LOG_ID") // unidirectional Parinte -->* Copil
   private List<ErrorComment> comments = new ArrayList<>();

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<ErrorTag> tags = new HashSet<>();


   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
