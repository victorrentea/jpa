//package victor.training.jpa.app;
//
//import org.hibernate.annotations.Subselect;
//import victor.training.jpa.app.entity.Child;
//
//import java.util.List;
//
//// SQL
//@Subselect("""
//            select p.id,
//                   p.name,
//                   string_agg(c.name, ',') as childrenNames
//            from parent p
//                     left join child c on p.id = c.parent_id
//            group by p.id, p.name
//            """)
//public record Sub(
//  String id,
//  String name,
//  String childrenNames) {
//}
