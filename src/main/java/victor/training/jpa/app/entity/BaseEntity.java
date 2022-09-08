package victor.training.jpa.app.entity;

import javax.persistence.*;

@MappedSuperclass
@SequenceGenerator(name = "Shmecher", allocationSize = 100)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(generator = "Shmecher")
    protected Long id;

    public Long getId() {
        return id;
    }
}

// "SELECT FROM BaseEntity" jpql/hql