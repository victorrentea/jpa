package victor.training.jpa.app.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class B {
   @Id
   @GeneratedValue
   private Long id;
   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn
   private Set<C> cList = new HashSet<>();
   private String name;
//   private List<C> cList;
   public B() {}

   public B(String name) {

      this.name = name;
   }
}
