package victor.training.jpa.app.repo;

import java.util.List;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchResult;

public interface TeacherRepoCustom {
	List<Teacher> getAllTeachersForYear(long yearId);


	List<TeacherSearchResult> search(TeacherSearchCriteria searchCriteria);
}
