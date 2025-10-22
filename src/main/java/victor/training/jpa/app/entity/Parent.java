package victor.training.jpa.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import victor.training.jpa.app.web.ParentDto;

import java.util.HashSet;
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
            LEFT JOIN FETCH p.country
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

  @OneToMany(mappedBy = "parent", cascade = ALL, fetch = FetchType.EAGER) // (aproape) NICIODATA!
//  @BatchSize(size = 20) // magic fix
  private Set<Child> children = new HashSet<>();

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