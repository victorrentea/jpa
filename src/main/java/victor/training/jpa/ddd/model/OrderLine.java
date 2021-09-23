package victor.training.jpa.ddd.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
public class OrderLine {
   @Id
   @GeneratedValue
   private Long id;

//   @ManyToOne
//   private Order order;

   private Long productId;

   private int itemCount;

   private BigDecimal itemPrice;

   private OrderLine() {} // for the eyes of Hivernate only
   public OrderLine(long productId, int itemCount, BigDecimal itemPrice) {
      this.productId = productId;
      this.itemCount = itemCount;
      this.itemPrice = Objects.requireNonNull(itemPrice);
   }


}
