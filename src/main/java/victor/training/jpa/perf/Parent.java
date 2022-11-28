package victor.training.jpa.perf;

import lombok.ToString.Exclude;
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
    @Exclude
    @JoinColumn(name = "PARENT_ID")
    @BatchSize(size = 20) // it will load children of many parent  in pages of 20 parents, using IN operator.
        // BUT!! you still have to trigger that lazy loading in an active transaction
    private Set<Child> children = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "PARENT_ID2")
    private Set<Child> children2 = new HashSet<>();

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

    public String getExtraField() {
        return this.extraField;
    }

    public Set<Child> getChildren2() {
        return this.children2;
    }

    public Parent setId(Long id) {
        this.id = id;
        return this;
    }

    public Parent setName(String name) {
        this.name = name;
        return this;
    }

    public Parent setExtraField(String extraField) {
        this.extraField = extraField;
        return this;
    }

    public Parent setChildren(Set<Child> children) {
        this.children = children;
        return this;
    }

    public Parent setChildren2(Set<Child> children2) {
        this.children2 = children2;
        return this;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Parent)) return false;
        final Parent other = (Parent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$extraField = this.getExtraField();
        final Object other$extraField = other.getExtraField();
        if (this$extraField == null ? other$extraField != null : !this$extraField.equals(other$extraField))
            return false;
        final Object this$children = this.getChildren();
        final Object other$children = other.getChildren();
        if (this$children == null ? other$children != null : !this$children.equals(other$children)) return false;
        final Object this$children2 = this.getChildren2();
        final Object other$children2 = other.getChildren2();
        if (this$children2 == null ? other$children2 != null : !this$children2.equals(other$children2)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Parent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $extraField = this.getExtraField();
        result = result * PRIME + ($extraField == null ? 43 : $extraField.hashCode());
        final Object $children = this.getChildren();
        result = result * PRIME + ($children == null ? 43 : $children.hashCode());
        final Object $children2 = this.getChildren2();
        result = result * PRIME + ($children2 == null ? 43 : $children2.hashCode());
        return result;
    }

    public String toString() {
        return "Parent(id=" + this.getId() + ", name=" + this.getName() +
               ", extraField=" + this.getExtraField() + ", children=" +
               this.getChildren() + ", children2=" + this.getChildren2() + ")";
    }
}