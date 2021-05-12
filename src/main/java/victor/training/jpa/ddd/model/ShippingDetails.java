package victor.training.jpa.ddd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ShippingDetails {
   private int shippingDaysEst;
   private int shippingCost;
   private boolean shippingToEasyBox;
   private String shippingProvider;
   private boolean shippingViaRegularPostOption;

   public ShippingDetails(int shippingDaysEst, int shippingCost, boolean shippingToEasyBox, String shippingProvider, boolean shippingViaRegularPostOption) {
      if (shippingDaysEst < 0) {
         throw new IllegalArgumentException();
      }
      this.shippingDaysEst = shippingDaysEst;
      this.shippingCost = shippingCost;
      this.shippingToEasyBox = shippingToEasyBox;
      this.shippingProvider = shippingProvider;
      this.shippingViaRegularPostOption = shippingViaRegularPostOption;
   }

   ShippingDetails() {} // i'm sorry- for hibernate only.
}
