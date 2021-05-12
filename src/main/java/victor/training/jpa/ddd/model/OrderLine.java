package victor.training.jpa.ddd.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderLine {
   @Id
   @GeneratedValue
   private Long id;

   @Embedded
   @Column(nullable = false)
   private ProductId productId;

   private int itemCount;

   private BigDecimal itemPrice;

   private OrderLine() {}
   public OrderLine(ProductId productId, int itemCount, BigDecimal itemPrice) {
      this.productId = Objects.requireNonNull(productId);
      this.itemCount = itemCount;
      this.itemPrice = itemPrice;
   }

   public BigDecimal getLinePrice() {
      return itemPrice.multiply(BigDecimal.valueOf(itemCount));
   }

   public Long getId() {
      return id;
   }

   public OrderLine withItemCount(int newItemCount) {
      return new OrderLine(productId, newItemCount, itemPrice);
   }

   public ProductId getProductId() {
      return productId;
   }

}
