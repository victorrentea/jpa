package victor.training.jpa.ddd.model;

import java.math.BigDecimal;
import java.util.Set;

public class ClientCode {
  public void method(Order order) {


//      o.setShippingTrackingNumber("asdjhasufjhasiuah97");
//      order.ship("asdjhasufjhasiuah97");
    Product product = new Product();
    order.getOrderLines().add(new OrderLine(product, 2, BigDecimal.ONE));
    order.getOrderLines().add(new OrderLine(product, 2, BigDecimal.ONE));

//    Tuple7<ProductId, Long, Set<Long>,Long,String,String>
  }
}
