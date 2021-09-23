package victor.training.jpa.ddd.model;

import lombok.Data;
import lombok.Value;

import javax.persistence.*;
import java.util.List;
@Embeddable
@Data
class ShippingDetails {
   private int shippingDaysEst;
   private int shippingCost;
   private boolean shippingToEasyBox;
   private String shippingProvider;
   private boolean shippingViaRegularPostOption;

}

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
   private ShippingDetails shippingDetails = new ShippingDetails();

   private boolean returnable;
   private int returnMaxDays;
   // TODO:
//   private List<ReturnCategory> returnCategories;

   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   @ManyToOne
   private Supplier supplier;

}
