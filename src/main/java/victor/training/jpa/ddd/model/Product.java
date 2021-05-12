package victor.training.jpa.ddd.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Product {
   @EmbeddedId
   private ProductId id;

   private String name;

   @Lob
   private String description;

   @Embedded
   private ShippingDetails shippingDetails = new ShippingDetails();

   private boolean returnable;
   private int returnMaxDays;
   // TODO:
//   private List<ReturnCategory> returnCategories;

   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   @ManyToOne//(fetch = FetchType.LAZY)
   private Supplier supplier;


   Product() {} // for hibernate

   public Product(ShippingDetails shippingDetails) {
      this.shippingDetails = Objects.requireNonNull(shippingDetails);
   }
}

