package victor.training.jpa.ddd.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order { // Aggrgate Root
   @Id
   @GeneratedValue
   private Long id;

   private LocalDateTime createDate = LocalDateTime.now();

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "ORDER_ID")
   private List<OrderLine> orderLines = new ArrayList<>();

   private BigDecimal totalPrice;

   public Long getId() {
      return id;
   }
   public BigDecimal getTotalPrice() {
      return totalPrice;
   }

   public List<OrderLine> getOrderLines() {
      return Collections.unmodifiableList(orderLines);
   }

   public void addLine(OrderLine orderLine) {
      orderLines.add(orderLine);
      totalPrice = orderLines.stream().map(OrderLine::getLinePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
   }
}
