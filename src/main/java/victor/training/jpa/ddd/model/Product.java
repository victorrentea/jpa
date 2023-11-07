package victor.training.jpa.ddd.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Embeddable
class ProductId {
   private Long id;
}
@Entity
@Data
@SequenceGenerator(name = "my", allocationSize = 50)// default
public class Product {
   @Id
   @GeneratedValue(generator = "my")//(strategy = GenerationType.IDENTITY)
   // IDENTITY not performant. why? when repo.save() hibernate HAS to hit the DB over network
   // = drama in batch imports!! = disables batching on INSERTS

   // SEQUENCE allows hibernate to cache eg 50 IDs in memory and assign the ID at repo.save() without A DB HIT
   private Long id;
//   @EmbeddedId
//   @GeneratedValue(custom generator)
//   private ProductId id;

   private String name;

   @Lob
   private String description;


   private boolean returnable;
   private int returnMaxDays;
   // TODO:
//   private List<ReturnCategory> returnCategories;

   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   @ManyToOne
   private Supplier supplier;

}
