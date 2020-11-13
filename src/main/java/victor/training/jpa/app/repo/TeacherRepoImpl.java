package victor.training.jpa.app.repo;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import lombok.Value;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

public class TeacherRepoImpl implements TeacherRepoCustom {

   @PersistenceContext
   private EntityManager em;


   // in the UI you only display id and name;

   @Override

   public List<TeacherSearchResult> searchEfficient(TeacherSearchCriteria searchCriteria) {
      String jpql = "SELECT new victor.training.jpa.app.repo.TeacherSearchResult(t.id, t.name)" +
                    " FROM Teacher t WHERE 1=1 ";
      Map<String, Object> params = new HashMap<>();

      if (searchCriteria.name != null) {
         jpql += " AND UPPER(t.name) LIKE UPPER('%' || :name || '%') ";
         params.put("name", searchCriteria.name);
      }

      if (searchCriteria.grade != null) {
         jpql += " AND t.grade = :grade ";
         params.put("grade", searchCriteria.grade);
      }


      TypedQuery<TeacherSearchResult> query = em.createQuery(jpql, TeacherSearchResult.class);
      for (String param : params.keySet()) {
         query.setParameter(param, params.get(param));
      }
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



//   @Override
//   public List<Teacher> search(TeacherSearchCriteria searchCriteria) {
//
//      JPAQuery<?> query = new JPAQuery<Void>(em);
//      BooleanExpression pred = Expressions.TRUE;
//
//
//      if (searchCriteria.grade != null) {
//         pred = pred.and(teacher.grade.eq(searchCriteria.grade));
//      }
//         if (searchCriteria.name != null) {
//            pred = pred.and(teacher.name.upper().like("%" + searchCriteria.name.toUpperCase() + "%"));
//         }
//
//      return query.select(teacher)
//             .from(teacher)
//             .where(pred)
//             .fetchAll()
//             .fetch();
//   }


//   @Override
//   public List<Teacher> search(TeacherSearchCriteria searchCriteria) {
//      CriteriaBuilder cb = em.getCriteriaBuilder();
//      CriteriaQuery<Teacher> criteriaQuery = cb.createQuery(Teacher.class);
//      Root<Teacher> root = criteriaQuery.from(Teacher.class);
//
//      List<Predicate> predicates = new ArrayList<>();
//
//      if (searchCriteria.grade != null) {
//         //  jpql += " AND t.grade = :grade ";
//         predicates.add(namePredicate(searchCriteria, cb, root));
//      }
//
//      if (searchCriteria.name != null) {
//         //  jpql += " AND t.name = :grade ";
//         predicates.add(getLike(searchCriteria, cb, root));
//      }
//
//      criteriaQuery.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
//
//      return em.createQuery(criteriaQuery).getResultList();
//   }
//
//   private Predicate getLike(TeacherSearchCriteria searchCriteria, CriteriaBuilder cb, Root<Teacher> root) {
//      return cb.like(cb.upper(root.get(Teacher_.name)), "%" + searchCriteria.name.toUpperCase() + "%");
//   }
//
//   private Predicate namePredicate(TeacherSearchCriteria searchCriteria, CriteriaBuilder cb, Root<Teacher> root) {
//      return cb.equal(root.get(Teacher_.grade), searchCriteria.grade);
//   }
//}

}

//thrown as JSON/XML/RMI
@Value
class TeacherSearchResult {
  long id;
  String name;
}
@Value
class TeacherSearchResultInDetailsPage {
  long id;
  String name;
  Grade grader;
}