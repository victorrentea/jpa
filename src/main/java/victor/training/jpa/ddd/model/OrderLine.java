package victor.training.jpa.ddd.model;

import lombok.experimental.NonFinal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderLine {
   @Id
   @GeneratedValue
   private Long id;

   @ManyToOne
   private Product product;
//   private Long productId;
//   private ProductSnapshot{name, desc, photo} product;

//   @Min(1)
   private int itemCount;
//@NonFinal
   private BigDecimal itemPrice;

   private OrderLine() {}

   public OrderLine(BigDecimal itemPrice, int itemCount) {
      if (itemCount <= 0) {
         throw new IllegalArgumentException();
      }
      this.itemPrice = Objects.requireNonNull(itemPrice);
      this.itemCount = itemCount;
   }

   public BigDecimal price() {
      return itemPrice.multiply(BigDecimal.valueOf(itemCount));
   }

   public Long getId() {
      return this.id;
   }

   public Product getProduct() {
      return this.product;
   }

   public int getItemCount() {
      return this.itemCount;
   }

   public BigDecimal getItemPrice() {
      return this.itemPrice;
   }

   public OrderLine setId(Long id) {
      this.id = id;
      return this;
   }

   public OrderLine setProduct(Product product) {
      this.product = product;
      return this;
   }



   public String toString() {
      return "OrderLine(id=" + this.getId() + ", order=" + ", product=" + this.getProduct() + ", itemCount=" + this.getItemCount() + ", itemPrice=" + this.getItemPrice() + ")";
   }
}
