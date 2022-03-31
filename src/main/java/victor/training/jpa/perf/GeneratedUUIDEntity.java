package victor.training.jpa.perf;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@GenericGenerator(name = "uuid", strategy = "victor.training.jpa.perf.UUIDGenerator")
public class GeneratedUUIDEntity {
   @Id
   // Option 1: hibernate sees a NON-null PK > triggers .merge() > useless SELECT before every new persist
   private String id = UUID.randomUUID().toString();

   // Option 2: Hibernate will call the custom generator at .save() ==> no SELECT
//   @GeneratedValue(generator = "uuid") // without this, h
//   private String id;


   private String name;
}

