package victor.training.jpa.magic.repo;

import org.springframework.beans.factory.annotation.Autowired;
import victor.training.jpa.magic.entity.Magic;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class MagicRepoImpl implements MagicRepoCustom {
   @Autowired
   EntityManager entityManager;
   @Override
   public List<Magic> search(String criteria) {
//      entityManager.crea
      return Collections.emptyList();
   }
}
