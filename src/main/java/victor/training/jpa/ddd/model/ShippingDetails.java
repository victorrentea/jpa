package victor.training.jpa.ddd.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


// evey embeddable should be made effectively immutable.
@Embeddable
public class ShippingDetails {
   private int shippingDaysEst;
   private int shippingCost;
   private boolean shippingToEasyBox;
   private String shippingProvider;
   private boolean shippingViaRegularPostOption;

   private ShippingDetails() {}
   public ShippingDetails(int shippingDaysEst, int shippingCost, boolean shippingToEasyBox, String shippingProvider, boolean shippingViaRegularPostOption) {
      this.shippingDaysEst = shippingDaysEst;
      this.shippingCost = shippingCost;
      this.shippingToEasyBox = shippingToEasyBox;
      this.shippingProvider = shippingProvider;
      this.shippingViaRegularPostOption = shippingViaRegularPostOption;
   }

   public int getShippingDaysEst() {
      return shippingDaysEst;
   }

   public int getShippingCost() {
      return shippingCost;
   }

   public boolean isShippingToEasyBox() {
      return shippingToEasyBox;
   }

   public String getShippingProvider() {
      return shippingProvider;
   }

   public boolean isShippingViaRegularPostOption() {
      return shippingViaRegularPostOption;
   }
}
