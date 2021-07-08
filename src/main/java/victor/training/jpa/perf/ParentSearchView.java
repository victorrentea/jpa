package victor.training.jpa.perf;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "PARENT_SEARCH")
@Entity
@Data
public class ParentSearchView {
   @Id
   private Long id;
   private String childrenNames;
}
