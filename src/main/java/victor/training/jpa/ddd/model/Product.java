package victor.training.jpa.ddd.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {
   @Id
   @GeneratedValue
   private Long id;

   private String name;

   @Lob
   private String description;

   @Embedded
   private ShippingDetails shippingDetails;

   private boolean returnable;
   private int returnMaxDays;
   // TODO:
//   private List<ReturnCategory> returnCategories;

   @Enumerated(EnumType.STRING)
   private ProductCategory category;

//   @ManyToOne
//   private Supplier supplier;
   private Long supplierId; // ! leave the FK in DB of course

}
