package victor.training.jpa.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import victor.training.jpa.app.web.ParentDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

//@SequenceGenerator(name = "parent_seq", sequenceName = "parent_seq", allocationSize = 1) // older versions of Hibernate
@Getter
@Setter
@Entity
@NamedQuery(name = "Parent.fetchWithChildren",
    query = """
            SELECT p
            FROM Parent p
            LEFT JOIN FETCH p.children
            LEFT JOIN FETCH p.pisici
            LEFT JOIN FETCH p.tags
            LEFT JOIN FETCH p.country
            ORDER BY p.name
        """)

@NamedNativeQuery(name="mirela",
    query = """
            select p.id,
                   p.name,
                   COALESCE(STRING_AGG(c.NAME, ',') within group (order by c.name asc), '') childrenNames
            from parent p
                     left join child c on p.id = c.parent_id
            group by p.id, p.name
            """,
resultClass = ParentDto.class,
resultSetMapping = "ParentDtoMapping")
@SqlResultSetMapping(
    name = "ParentDtoMapping",
    classes = @ConstructorResult(
        targetClass = ParentDto.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "childrenNames", type = String.class)
        }
    )
)
public class Parent {
   @Id
   @GeneratedValue// ⚠️older Hibernate versions might need (strategy = GenerationType.SEQUENCE, generator = "parent_seq")
   private Long id;

   private String name;
   private Integer age;


   // create table PUBLIC.PARENT_TAGS (
  //    PARENT_ID BIGINT not null,
  //    TAGS      CHARACTER VARYING(255),
  //    constraint FK8RIK8MD4TEIRQHH8G44W2T6I8
  //        foreign key (PARENT_ID) references PUBLIC.PARENT
  //);

  @ElementCollection //adica entitati copii da fara ID
  private Set<String> tags = new HashSet<>();

  @OneToMany
  @JoinColumn(name = "STAPAN_ID")
  private List<Pisica> pisici = new ArrayList<>();

  @OneToMany(mappedBy = "parent", cascade = ALL, fetch = FetchType.EAGER) // (aproape) NICIODATA!
//  @BatchSize(size = 20) // magic fix
  @OrderColumn(name = "index_copil")
  private List<Child> children = new ArrayList<>();

   @ManyToOne
   private Country country; // surprise ! nu doar @OneToMany => +1 SELECT ci si @ManyToOne

   public Parent() {}
   public Parent(String name) {
      this.name = name;
   }

   public Parent addChild(Child child) {
      children.add(child);
      child.setParent(this);
      return this;
   }

   public String toString() {
      return "Parent{id=" + id + ", name='" + name + "'}";
   }
}