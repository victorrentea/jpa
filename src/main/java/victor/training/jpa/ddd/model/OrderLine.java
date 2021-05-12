package victor.training.jpa.ddd.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class OrderLine {
   @Id
   @GeneratedValue
   private Long id;

//   @Column(updatable = false, insertable = false)
//   private Long orderId;

   @ManyToOne
   private Order order;

   @ManyToOne
   private Product product;

   private int itemCount;

   private BigDecimal itemPrice;
}
