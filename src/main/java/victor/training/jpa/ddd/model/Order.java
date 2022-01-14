package victor.training.jpa.ddd.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.*;

@Entity
@Table(name = "ORDERS")
public class Order {
   @Id
   @GeneratedValue
   private Long id;

   private LocalDateTime createDate;
   private LocalDate estShippingDate;

   public Order setEstShippingDate(LocalDate estShippingDate) {
      if (estShippingDate.isBefore(LocalDate.now())) {
         throw new IllegalArgumentException();
      }
      this.estShippingDate = estShippingDate;
      return this;
   }

   @OneToMany(mappedBy = "order")
   @JoinColumn
   private Set<OrderLine> orderLines = new HashSet<>();


   private BigDecimal totalPrice;

   public Order() {
   }

   public Long getId() {
      return this.id;
   }

   public LocalDateTime getCreateDate() {
      return this.createDate;
   }

   public Set<OrderLine> getOrderLines() {
      return unmodifiableSet(this.orderLines);
   }

   public BigDecimal getTotalPrice() {
      return this.totalPrice;
   }

   public Order setId(Long id) {
      this.id = id;
      return this;
   }

   public Order setCreateDate(LocalDateTime createDate) {
      this.createDate = createDate;
      return this;
   }


   public String toString() {
      return "Order(id=" + this.getId() + ", createDate=" + this.getCreateDate() + ", orderLines=" + this.getOrderLines() + ", totalPrice=" + this.getTotalPrice() + ")";
   }

   public void addLine(OrderLine line) {
      orderLines.add(line);
//      line.order = this;
      totalPrice = orderLines.stream().map(OrderLine::price).reduce(BigDecimal.ZERO, BigDecimal::add);
   }
}
