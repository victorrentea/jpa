package victor.training.jpa.perf;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @BatchSize(size = 20)

    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL)
    private Set<Child> children = new HashSet<>();

    private Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Parent addChild(Child child) {
        children.add(child);
        child.setParent(this);
        return this;
    }

    public Set<Child> getChildren() {
        return unmodifiableSet(children);
    }
}