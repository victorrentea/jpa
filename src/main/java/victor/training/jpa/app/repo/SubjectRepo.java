package victor.training.jpa.app.repo;

import java.util.List;

import victor.training.jpa.app.common.data.CustomJpaRepository;
import victor.training.jpa.app.domain.entity.Subject;

public interface SubjectRepo extends CustomJpaRepository<Subject, Long> {

	public Subject getByName(String name);
	
	public List<Subject> getByHolderTeacherName(String teacherName);
	
	public List<Subject> getByActiveTrue();

//	Optional<Subject> cbd_findById(Long aLong);
}
