package victor.training.jpa.app.repo;

import java.util.List;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchResult;

public interface TeacherRepoCustom {

	List<Teacher> search(TeacherSearchCriteria searchCriteria);

	List<TeacherSearchResult> searchCriteriaMetamodel(TeacherSearchCriteria searchCriteria);

	List<Teacher> searchQueryDSL(TeacherSearchCriteria searchCriteria);
}
