//package victor.training.jpa.app.repo;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import org.apache.commons.lang3.StringUtils;
//
//import victor.training.jpa.app.domain.entity.Teacher;
//import victor.training.jpa.app.domain.entity.TeachingActivity;
//import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
//
//public class TeacherRepoImpl implements TeacherRepoCustom{
//
//	@PersistenceContext
//	private EntityManager em;
//
//
//
//
//	// TODO do a mistake in JPQL, WHICH TEACH COURSES OR LABS
////	public List<Teacher> getAllTeachersForYear(long yearId) {
////		return query.getResultList();
////	}
//
//
//	@Override
//	public List<TeachingActivity> searchActivity(ActivitySearchCriteria criteria) {
//		Map<String, Object> params = new HashMap<>();
//		String jpql = "SELECT a FROM TeachingActivity a";
//		TypedQuery<TeachingActivity> query = em.createQuery(jpql, TeachingActivity.class);
//		for (String key : params.keySet()) {
//			query.setParameter(key, params.get(key));
//		}
//		return query.getResultList();
//	}
//
//}
