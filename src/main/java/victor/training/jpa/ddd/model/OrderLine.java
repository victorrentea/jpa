package victor.training.jpa.ddd.model;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
public class OrderLine {
   @Id
   @GeneratedValue
   private Long id;

   @ManyToOne
   private Order order;

   @ManyToOne
   private Product product;

   private int itemCount;

   private BigDecimal itemPrice;

   protected OrderLine() {} // for Hibernate only


   public OrderLine(Product product, int itemCount, BigDecimal itemPrice) {
      this.product = product;
      this.itemCount = itemCount;
      this.itemPrice = itemPrice;
   }

   // "separate behavior from state"
   public BigDecimal linePrice() { // OOP keep behavior next to state
      return itemPrice.multiply(BigDecimal.valueOf(itemCount));
      // in a UTIL/Helper
   }

   // what if I need more stuff to other services @Milkov: then that logic should be in a service
//   public BigDecimal heavyBizLogic(AnotherService omg, ANotherRepo wtf, ExternaAPiClient no) { // OOP keep behavior next to state
//      return itemPrice.multiply(BigDecimal.valueOf(itemCount));
//   }
//
//   public void alsoDontTakeLargeObjectsIn(Customer20Fields hugeObject) {
//   }

   // also don't add dozens of lines of biz logic


}
