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
@SequenceGenerator(name = "MySeqGen")
// hibernate gets a window of 50 IDs at once SO NO netwwork calls are needed for the next 49 IDs assigned.

// some DBs (mysql) does not support sequences.
// effect=disables write-behind for INSERTS (~= .saveAndFlush()), you cannot use batch mode anymore!!
// why? because after you do a repo.save() JPA HAS to give you the new ID (setting it in the @Id field)
public class IDDocument {

    @Id
    @GeneratedValue(generator = "MySeqGen") // if you do not specify a generator, it will fetch one ID at a time
    private Long id;
    @ManyToOne
    private IDDocumentType type;
}
