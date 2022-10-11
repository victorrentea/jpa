package victor.training.jpa.perf;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Child {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    private String name;
    private Integer age;

    private Child() {
    }

    public Child(String name) {
        this.name = name;
    }
}
