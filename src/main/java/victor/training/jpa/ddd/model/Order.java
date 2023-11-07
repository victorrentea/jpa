package victor.training.jpa.ddd.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Aggregate Root taking care of the consistency of itself + collection of OrderLine
@Entity
//@Data // BAD: because
// 1) overrides hashcode/eq
// 2) toString could cause lazy loading
// 3) do we really have to have SETTERS on all fields ??
@Getter
@Table(name = "ORDERS")
public class Order {
   @Id
   @GeneratedValue
   private Long id;

   @Setter
   @NotNull
   private LocalDateTime createDate;

   @OneToMany(mappedBy = "order")
   private List<OrderLine> orderLines = new ArrayList<>();

   // @Embedded InvoiceDetails {} invoice;

//   @Setter
//   private String invoiceName;
//   @Setter
//   private String invoiceAddress;
//   @Setter
//   private String invoiceVATCode;
//   @Setter
//   private boolean invoiceLegalEntity;



   @Setter
   private BigDecimal totalPrice;

   public void addLine(OrderLine orderLine) {
      // rules to make sure i can't add same product twice
      orderLines.add(orderLine);
   }
   public List<OrderLine> getOrderLines() {
      return Collections.unmodifiableList(orderLines);
   }

   private String shippingTrackingNumber; // once the order is scheduled for shipping, it MUST have a shippingTrackingNumber

   private String paymentId;

   private Status status = Status.CREATED;

   public enum Status {
      CREATED,
      PAID,
      SHIPPED,
      COMPLETED
   }

   public void pay(String paymentId) {
      if (status != Status.CREATED) {
         throw new IllegalStateException();
      }
      this.paymentId = paymentId;
      status = Status.PAID;
   }

   public void ship(String shippingTrackingNumber) {
//      if (flagRepo.isBulgaria())
      if (status != Status.PAID) {
         throw new IllegalStateException();
      }
      this.shippingTrackingNumber = shippingTrackingNumber;
      status = Status.SHIPPED;
   }

   public void complete() {
      if (status != Status.SHIPPED) {
         throw new IllegalStateException();
      }
      status = Status.COMPLETED;
   }
}


