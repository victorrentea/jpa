package victor.training.jpa.app.repo;

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

public class TeacherRepoImpl implements TeacherRepoCustom{
	
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
	public List<TeachingActivity> searchActivity(ActivitySearchCriteria criteria) {
		Map<String, Object> params = new HashMap<>();
		String jpql = "SELECT a FROM TeachingActivity a WHERE 1=1 ";
		
		if (StringUtils.isNotBlank(criteria.subject)) {
			jpql += " AND UPPER(a.subject.name) LIKE UPPER('%' || :subject || '%') ";
			params.put("subject", criteria.subject);
		}
		
		if (StringUtils.isNotBlank(criteria.roomId)) {
			jpql += " AND UPPER(a.roomId) = :roomId";
			params.put("roomId", criteria.roomId);
		}

		if (criteria.day != null) {
			jpql += " AND a.day = :day";
			params.put("day", criteria.day);
		}
		
		TypedQuery<TeachingActivity> query = em.createQuery(jpql, TeachingActivity.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		// TODO implement pagination
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
