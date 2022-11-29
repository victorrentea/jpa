package victor.training.jpa.perf;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Child implements ChildForUC32{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @ManyToOne
    private Parent parent;

    private Child() {
    }

    public Child(String name) {
        this.name = name;
    }
}
