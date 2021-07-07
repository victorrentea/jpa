package victor.training.jpa.app.repo;

import victor.training.jpa.app.domain.entity.StudentsGroup;
import victor.training.jpa.app.repo.common.CustomJpaRepository;

public interface StudentGroupRepo extends CustomJpaRepository<StudentsGroup, Long> {
}
