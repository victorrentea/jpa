package victor.training.jpa.app.repo;

import java.util.List;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

public interface TeacherRepoCustom {
//	List<Teacher> getAllTeachersForYear(long yearId);

	List<Teacher> search(TeacherSearchCriteria searchCriteria);
}
