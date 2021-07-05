package victor.training.jpa.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.common.data.EntityRepository;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.TeachingActivity;

public interface SubjectRepo extends EntityRepository<Subject, Long> {

   @Query("SELECT x from TeachingActivity x")
   List<TeachingActivity> searchByCeva();
//	public Subject getByName(String name);
//
//	public List<Subject> getByHolderTeacherName(String teacherName);
//
//	public List<Subject> getByActiveTrue();
}
