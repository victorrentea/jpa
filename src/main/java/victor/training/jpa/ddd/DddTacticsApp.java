package victor.training.jpa.ddd;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.ddd.model.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@SpringBootApplication
public class DddTacticsApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(DddTacticsApp.class, args);
   }
   @Autowired
   private EntityManager em;
   @Override
   @Transactional
   public void run(String... args) throws Exception {

//
      Order order = new Order();
      ShippingDetails shippingDetails = new ShippingDetails(2,10,true,"FANCOURIER", true);
      Product product = new Product(shippingDetails);

      product.setId(new ProductId(ProductCategory.HOM, 1L));
      em.persist(product);


      order.addLine(new OrderLine(product.getId(), 1, BigDecimal.TEN));
      order.addLine(new OrderLine(product.getId(), 2, BigDecimal.TEN));

      em.persist(order);


      deepBizLogic(product.getId());

      em.createQuery("SELECT new victor.training.jpa.ddd.MyDto(p.name, l.itemCount) " +
                     "FROM OrderLine l JOIN Product p ON p.id = l.productId", MyDto.class);

//      em.find(OrderLine.class, 1L);


      // - Avoiding bidirectional links
      //- Limit relations via numeric ids
      //- ID Types (microtypes) << HARD
      //- Semantic IDs << HARDER
      //- Decomposing large entities
      //- Enforcing Entity Consistency & Validation>> CONS: testing is harded
   }


   public void deepBizLogic(ProductId id) {

   }
}

@Value
class MyDto {
   private String productName;
   private int itemCount;
}
