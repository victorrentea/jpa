package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.jpa.app.entity.TeacherDetails;

public interface TeacherDetailsRepo extends JpaRepository<TeacherDetails, Long> {
}
