package victor.training.jpa.perf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class IDDocumentType {
  @Id
  @GeneratedValue
  private Long id;
  private String label;
  public IDDocumentType() {}

  public IDDocumentType(String label) {
    this.label = label;
  }
}
