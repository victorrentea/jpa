package victor.training.jpa.perf;

import org.hibernate.annotations.BatchSize;
import org.hibernate.collection.internal.PersistentSet;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

@Entity
//@NamedQueries({@NamedQuery("SELECT CA BOU NU MERGE", )})
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @BatchSize(size = 200)
    @OneToMany(cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    @JoinColumn(name = "PARENT_ID")
    private Set<Child> children = new HashSet<>();

//    @OneToMany(cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
//    @JoinColumn(name = "PARENT_ID")
//    private Set<Child> childrenAdoptati = new HashSet<>();

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
        return this;
    }

    public Set<Child> getChildren() {
        return children;
    }
}