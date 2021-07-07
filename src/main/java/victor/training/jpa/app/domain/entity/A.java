package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@ToString
public class A {
   @Id
   @GeneratedValue
   private Long id;
   @OneToMany(cascade = ALL)
   @JoinColumn
//   private Set<B> bList = new HashSet<>(); // daca nu iti pasa de ordine
//   @OrderBy("name ASC")
   @OrderColumn(name= "INDEX")
   private List<B> bList = new ArrayList<>();

//   @Override
//   public boolean equals(Object o) {
//      if (this == o) return true;
//      if (o == null || getClass() != o.getClass()) return false;
//      A a = (A) o;
//      return Objects.equals(id, a.id) && Objects.equals(bList, a.bList);
//   }
//
//   @Override
//   public int hashCode() {
//      return Objects.hash(id, bList);
//   }
}
