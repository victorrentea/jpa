package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class C {
   @Id
   @GeneratedValue
   private Long id;
   private String name;

   public C() {}
   public C(String name) {
      this.name = name;
   }

}
