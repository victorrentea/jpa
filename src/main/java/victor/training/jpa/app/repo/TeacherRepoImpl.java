package victor.training.jpa.app.repo;

import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public List<Teacher> search(TeacherSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
      // TODO: see TeacherSearchRepo for alternatives of writing a dynamic query
      return Collections.emptyList();
   }

}




