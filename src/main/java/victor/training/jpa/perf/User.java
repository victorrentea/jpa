package victor.training.jpa.perf;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
  @Id
  private Long id;
  private String name;

  private User() {
  }

  public User(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
