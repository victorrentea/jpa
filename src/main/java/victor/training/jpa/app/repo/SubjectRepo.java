package victor.training.jpa.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.common.CustomJpaRepository;
import victor.training.jpa.app.entity.Subject;

public interface SubjectRepo extends CustomJpaRepository<Subject, Long> {


	// SOLUTION:
	@Query("SELECT s FROM Subject s" +
		   " LEFT JOIN FETCH s.activities" +
		   " LEFT JOIN FETCH s.holderTeacher" +
		   " WHERE s.id = ?1")
	Subject findByIdWithActivities(Long subjectId);

	Subject getByName(String name);
	
	List<Subject> getByHolderTeacherName(String teacherName);
	
	List<Subject> getByActiveTrue();

//	Optional<Subject> cbd_findById(Long aLong);
}
