package victor.training.jpa.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.TeachingActivity;

public interface SubjectRepo extends JpaRepository<Subject, Long> {


   @Query("SELECT x from TeachingActivity x")
   List<TeachingActivity> searchByCeva();

   @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.activities LEFT JOIN FETCH s.holderTeacher")
   Subject findWithActivities(long id);
//	public Subject getByName(String name);
//
//	public List<Subject> getByHolderTeacherName(String teacherName);
//
//	public List<Subject> getByActiveTrue();
}
