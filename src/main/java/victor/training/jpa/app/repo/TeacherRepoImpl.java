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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static victor.training.jpa.app.domain.entity.QTeacher.teacher;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager em;

   public void method() {

      // doamne fereste casta e validat doar cand il executi
//      em.createQuery("SELECT s FROM Subject s LEFT JOIN FETCH s.students WHERE s.holderTeacher.name = ?1", SubjectRepo.class)
//      em.createNamedQuery("q1");
   }

   @Override
   public List<Teacher> search(TeacherSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
      String jpql = "SELECT t FROM Teacher t WHERE 1=1 ";
      Map<String, Object> params = new HashMap<>();

      if (searchCriteria.name != null) {
         jpql += "  AND t.name = :teacherName  ";
         params.put("teacherName", searchCriteria.name);
      }

      TypedQuery<Teacher> query = em.createQuery(jpql, Teacher.class);
      for (String param : params.keySet()) {
         query.setParameter(param, params.get(param));
      }
      return query.getResultList();
   }


   @Override
   public List<TeacherSearchResult> searchCriteriaMetamodel(TeacherSearchCriteria searchCriteria) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<TeacherSearchResult> criteriaQuery = cb.createQuery(TeacherSearchResult.class);
      Root<Teacher> root = criteriaQuery.from(Teacher.class);

      List<Predicate> predicates = new ArrayList<>();

      if (searchCriteria.name != null) {
         predicates.add(searchByName(cb, root, searchCriteria.name));
      }
      if (searchCriteria.grade != null) {
         predicates.add(cb.equal(root.get(Teacher_.grade), searchCriteria.grade));
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

   private static Predicate searchByName(CriteriaBuilder cb, Root<Teacher> root, String teacherName) {
      return cb.equal(root.get(Teacher_.name), teacherName);
   }


   @Override
   public List<Teacher> searchQueryDSL(TeacherSearchCriteria searchCriteria) {
      JPAQuery<?> query = new JPAQuery<Void>(em);
      BooleanExpression pred = Expressions.TRUE;

      if (searchCriteria.name != null) {
         pred = pred.and(teacher.name.eq(searchCriteria.name));
      }

      return query.select(teacher)
          .from(teacher)
          .where(pred)
          .fetchAll()
          .fetch();
   }


}




