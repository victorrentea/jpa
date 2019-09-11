package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public
class Country2 {
    @Id
    private Long id;
    private String name;
    private String region;
    private String continent;
    private int population;
    public Country2() {
    }
    public Country2(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}