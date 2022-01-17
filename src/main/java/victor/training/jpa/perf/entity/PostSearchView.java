package victor.training.jpa.perf.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "POST_SEARCH_VIEW")
@Entity
@Getter
@Setter
@ToString
// TODO @Subselect vs schema.sql
public class PostSearchView {
   @Id
   private Long id;
   private String title;
   private String commentTitles;
}
