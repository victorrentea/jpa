package victor.training.jpa.perf;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Parent implements ParentForUC32 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String extraField;

    @OneToMany(cascade = CascadeType.ALL
//            ,fetch = FetchType.EAGER // it's still bad for performance. It only helps to ....
            // it's worse now, because these children will be LOADED EVERY TIME a Parent is retrieved by JPA
    )
    @JoinColumn(name = "PARENT_ID")
    @BatchSize(size = 20) // it will load children of many parent  in pages of 20 parents, using IN operator.
        // BUT!! you still have to trigger that lazy loading in an active transaction
    private Set<Child> children = new HashSet<>();

    private Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Parent addChild(Child child) {
        children.add(child);
        return this;
    }

    @Override
    public Set<Child> getChildren() {
        return children;
    }
}