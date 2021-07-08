package victor.training.performance.batch.core.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SequenceGenerator(name = "PersonIdGenerator", allocationSize = 100, sequenceName = "SEQ_PERSON")
public class Person {
    @Id
    @GeneratedValue(generator = "PersonIdGenerator")
    private Long id;
    private String name;
    @ManyToOne
    private City city;
    public Person(String name) {
        this.name = name;
    }
    public Person() {}
}
