package victor.training.jpa.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.common.CustomJpaRepository;
import victor.training.jpa.app.entity.Subject;

public interface SubjectRepo extends CustomJpaRepository<Subject, Long> {


	Subject getByName(String name);
	
	List<Subject> getByHolderTeacherName(String teacherName);
	
	List<Subject> getByActiveTrue();



	@Query("SELECT s FROM Subject s LEFT JOIN FETCH s.activities" )
	Subject findSubjectWithActivities(Long subjectId);
}
