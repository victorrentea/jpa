package victor.training.jpa.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CountryRegion {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
