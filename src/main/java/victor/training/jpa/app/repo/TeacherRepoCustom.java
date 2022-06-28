package victor.training.jpa.app.repo;

import java.util.List;

import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

public interface TeacherRepoCustom {
	List<Teacher> search(TeacherSearchCriteria searchCriteria);
}
