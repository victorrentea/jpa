package victor.training.jpa.magic;

import org.springframework.data.jpa.domain.Specification;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.entity.Magic_;

import javax.persistence.criteria.Expression;
import java.time.LocalDateTime;

public class MagicSpec {
   public static Specification<Magic> createdBetween(LocalDateTime fromDate, LocalDateTime toDate) {
      return (root, query, cb) -> cb.between(root.get(Magic_.createdTime), fromDate, toDate);
   }

   public static Specification<Magic> nameLike(String namePart) {
      return (root, query, cb) -> cb.like(cb.upper(root.get(Magic_.name)), cb.upper(cb.literal("%" + namePart + "%")));
   }
}
