package victor.training.jpa.ddd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class ProductId implements Serializable {
   private String productId;
   private ProductId() {}
   public ProductId(ProductCategory category, Long id) {
      productId = category.name() + '-' + UUID.randomUUID().toString();
   }
}
