package victor.training.jpa.perf;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

// When using a UUID as PK:
// @GenericGenerator(name = "uuid", strategy = "victor.training.jpa.perf.UUIDGenerator") +  @GeneratedValue(generator = "uuid") private String id;
@Entity
@Getter
@Setter
public class IDDocument {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private IDDocumentType type;
}
