package victor.training.jpa.app.repo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.domain.Specification;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static victor.training.jpa.app.domain.entity.QTeacher.teacher;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager em;

   // TODO do a mistake in JPQL

//
//   public List<Teacher> getAllTeachersForYear(long yearId) {
//      TypedQuery<Teacher> query = em.createQuery(
//          "SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE "
//          + "a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId) "
//          + "OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)",
//          Teacher.class);
//      query.setParameter("yearId", yearId);
//      return query.getResultList();
//   }

   @Override
   public List<Teacher> search(TeacherSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
      List<String> jpql= new ArrayList<>();
      jpql.add("SELECT new t FROM Teacher t WHERE 1=1");
      Map<String, Object> params = new HashMap<>();


      if (searchCriteria.name != null) {
         jpql.add("AND UPPER(t.name) LIKE UPPER('%' || :name || '%')");
         params.put("name", searchCriteria.name);
      }

      if (searchCriteria.grade != null) {
         jpql.add("AND t.grade = :grade");
         params.put("grade", searchCriteria.grade);
      }
//      if (searchCriteria.grade != null) {
//         jpql.add("AND EXISTS (SELECT * FROM Students s where t IN s.teachers)");
//         params.put("grade", searchCriteria.grade);
//      }

      TypedQuery<Teacher> query = em.createQuery(String.join("    ", jpql), Teacher.class);
      for (String param : params.keySet()) {
         query.setParameter(param, params.get(param));
      }
      return query.getResultList();
   }

   public List<Teacher> searchQueryDSL(TeacherSearchCriteria searchCriteria) {
      JPAQuery<?> query = new JPAQuery<Void>(em);
      BooleanExpression pred = Expressions.TRUE;

      if (searchCriteria.grade != null) {
         pred = pred.and(teacher.grade2.eq(searchCriteria.grade));
      }
      if (searchCriteria.name != null) {
         pred = pred.and(teacher.name.upper()
             .like("%" + searchCriteria.name.toUpperCase() + "%"));
      }

      return query.select(teacher)
          .from(teacher)
          .where(pred)
          .fetchAll()
          .fetch();
   }


   public List<TeacherSearchResult> searchCriteriaMetamodel(TeacherSearchCriteria searchCriteria) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<TeacherSearchResult> criteriaQuery = cb.createQuery(TeacherSearchResult.class);
      Root<Teacher> root = criteriaQuery.from(Teacher.class);

      List<Predicate> predicates = new ArrayList<>();

      if (searchCriteria.grade != null) {
         predicates.add(hasGrade(cb, root, searchCriteria.grade));
      }

      if (searchCriteria.name != null) {
         predicates.add(hasNameLike(cb, root, searchCriteria.name));
      }

      criteriaQuery.select(cb.construct(
             TeacherSearchResult.class,
             root.get(Teacher_.id),
             root.get(Teacher_.name),
             root.get(Teacher_.grade)
          ))
          .where(cb.and(predicates.toArray(new Predicate[0])));

      return em.createQuery(criteriaQuery).getResultList();
   }

   private Predicate hasNameLike(CriteriaBuilder cb, Root<Teacher> root, String name) {
      return cb.like(cb.upper(root.get(Teacher_.name)), "%" + name.toUpperCase() + "%");
   }

   private Predicate hasGrade(CriteriaBuilder cb, Root<Teacher> root, Grade grade) {
      return cb.equal(root.get(Teacher_.grade), grade);
   }



   public static java.util.function.Predicate<Teacher> hasNameLikePred(String name) {
      return t -> t.getName().equals(name);
   }


   private Predicate hasGradeSpec(CriteriaBuilder cb, Root<Teacher> root, Grade grade) {
      return cb.equal(root.get(Teacher_.grade), grade);
   }



}




