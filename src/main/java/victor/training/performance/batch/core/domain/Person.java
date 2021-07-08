package victor.training.performance.batch.core.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@SequenceGenerator(name = "PersonIdGenerator", allocationSize = 100, sequenceName = "SEQ_PERSON")
public class Person {

//    @Id
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @GeneratedValue(generator = "uuid")
//    @Column(columnDefinition = "UNIQUEIDENTIFIER")
//    @Type(type = "uuid-char")
//    private UUID id;

    @Id
    @GeneratedValue(generator = "PersonIdGenerator")
    private Long id;
//    private String externalId = UUID.randomUUID().toString();
    private String name;
    @ManyToOne
    private City city;
    public Person(String name) {
        this.name = name;
    }
    public Person() {}
}
