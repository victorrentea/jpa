package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "POST_SEARCH_VIEW")
// Worse as it's not validated until usage in runtime
//@Subselect("select P.ID, P.TITLE, STRING_AGG(c.TITLE, ',') within group (order by c.title desc) comment_titles\n" +
//           "from POST P\n" +
//           "         left join COMMENTS C on P.ID = C.POST_ID\n" +
//           "group by p.ID, P.TITLE")
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
