package victor.training.jpa.ddd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import victor.training.jpa.ddd.model.Order;
import victor.training.jpa.ddd.model.OrderLine;

import java.math.BigDecimal;

@SpringBootApplication
public class DddApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(DddApp.class, args);
   }

   @Override
   public void run(String... args) throws Exception {
      Order order = new Order();

      OrderLine line = new OrderLine(BigDecimal.TEN, 2);
//      line.setItemCount(2);
//      line.setItemPrice(BigDecimal.TEN);

//      line.setOrder(order);
      order.addLine(line);
//      repo.save(order)
   }

}
