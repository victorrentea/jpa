package victor.training.jpa.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Table(name = "PARENT_SEARCH_VIEW")
public class ParentSearchView {
  @Id
    private Long id;
    private String name;
    private String childrenNames;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChildrenNames() {
        return childrenNames;
    }
}
