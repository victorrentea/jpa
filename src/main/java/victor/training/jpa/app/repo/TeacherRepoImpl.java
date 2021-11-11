package victor.training.jpa.app.repo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher_;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static victor.training.jpa.app.domain.entity.QTeacher.teacher;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public List<Teacher> search(TeacherSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
      // TODO: see TeacherSearchRepo for alternatives of writing a dynamic query
      return Collections.emptyList();
   }

}




