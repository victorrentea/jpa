package victor.training.jpa.magic.repo;

import victor.training.jpa.magic.entity.Magic;

import java.util.List;

public interface MagicRepoCustom {
   List<Magic> search(String criteria);
}
