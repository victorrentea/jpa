package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class Room {
   @Id
   @GeneratedValue
   private Long id;

   private String name;
}
