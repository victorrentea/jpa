package victor.training.jpa.perf;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Child {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    private Child() {
    }

    public Child(String name) {
        this.name = name;
    }
}
