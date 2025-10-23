package victor.training.jpa.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Pisica {
  @Id
  @GeneratedValue
  Long id;
  String nume;
  int oreSomn = 16;
}
