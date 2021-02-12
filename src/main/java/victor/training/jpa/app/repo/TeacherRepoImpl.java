package victor.training.jpa.app.repo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.ws.ServiceMode;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.domain.entity.Teacher_;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

import static victor.training.jpa.app.domain.entity.QTeacher.teacher;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager em;

   // TODO do a mistake in JPQL
   public List<Teacher> getAllTeachersForYear(long yearId) {
      TypedQuery<Teacher> query = em.createQuery("SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE "
                                                 + "a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId) "
                                                 + "OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)",
          Teacher.class);
      query.setParameter("yearId", yearId);
      return query.getResultList();
   }

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

   public List<Teacher> searchQueryDSL(TeacherSearchCriteria searchCriteria) {
      JPAQuery<?> query = new JPAQuery<Void>(em);
      BooleanExpression pred = Expressions.TRUE;

      if (searchCriteria.grade != null) {
         pred = pred.and(teacher.grade.eq(searchCriteria.grade));
      }
      if (searchCriteria.name != null) {
         pred = pred.and(teacher.name.upper().like("%" + searchCriteria.name.toUpperCase() + "%"));
      }

      return query.select(teacher)
          .from(teacher)
          .where(pred)
          .fetchAll()
          .fetch();
   }


   public List<Teacher> searchCriteriaMetamodel(TeacherSearchCriteria searchCriteria) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Teacher> criteriaQuery = cb.createQuery(Teacher.class);
      Root<Teacher> root = criteriaQuery.from(Teacher.class);

      List<Predicate> predicates = new ArrayList<>();

      if (searchCriteria.grade != null) {
         predicates.add(hasGrade(searchCriteria.grade, cb, root));
      }

      if (searchCriteria.name != null) {
         predicates.add(hasNameLike(searchCriteria.name, cb, root));
      }

      criteriaQuery.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

      return em.createQuery(criteriaQuery).getResultList();
   }

   private Predicate hasNameLike(String name, CriteriaBuilder cb, Root<Teacher> root) {
      return cb.like(cb.upper(root.get(Teacher_.name)), "%" + name.toUpperCase() + "%");
   }

   private Predicate hasGrade(Grade grade, CriteriaBuilder cb, Root<Teacher> root) {
      return cb.equal(root.get(Teacher_.grade), grade);
   }

}




