package victor.training.jpa.app.repo;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

import java.util.List;

public interface TeacherRepoCustom {
   List<TeacherSearchResult> searchEfficient(TeacherSearchCriteria searchCriteria);

   List<Teacher> search(TeacherSearchCriteria searchCriteria);
}
