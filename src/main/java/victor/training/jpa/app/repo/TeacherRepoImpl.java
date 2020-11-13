package victor.training.jpa.app.repo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager em;


   @Override
   public List<Teacher> search(TeacherSearchCriteria searchCriteria) {
      String jpql = "SELECT t FROM Teacher t WHERE 1=1 ";
      Map<String, Object> params = new HashMap<>();

      if (searchCriteria.name != null) {
         jpql += " AND UPPER(t.name) LIKE UPPER('%' || :name || '%') ";
         params.put("name", searchCriteria.name);
      }

      if (searchCriteria.grade != null) {
         jpql += " AND t.grade = :grade ";
         params.put("grade", searchCriteria.grade);
      }


      TypedQuery<Teacher> query = em.createQuery(jpql, Teacher.class);
      for (String param : params.keySet()) {
         query.setParameter(param, params.get(param));
      }
      return query.getResultList();
   }
}
