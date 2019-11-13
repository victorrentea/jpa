package victor.training.jpa.perf;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class History {
    @Id
    @GeneratedValue
    private Long id;
}
