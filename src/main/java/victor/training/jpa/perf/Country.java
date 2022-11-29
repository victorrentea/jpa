package victor.training.jpa.perf;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Country {
  @Id
  private Long id;
  private String name;
  @ManyToOne
  private Region region;
  private String continent;
  private int population;

  private Country() {
  }

  public Country(Long id, String name) {
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
