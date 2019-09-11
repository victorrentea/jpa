package victor.training.jpa.app.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.ActivitySearchCriteria;
import victor.training.jpa.app.domain.entity.TeachingActivity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeachingActivityRepoImpl implements TeachingActivityRepoCustom
{
    @Autowired
    private EntityManager em;
    @Override
    public List<TeachingActivity> search(ActivitySearchCriteria criteria) {
//        @Query("SELECT ta FROM TeachingActivity ta WHERE paiii, daca am dala pun altfel nu")
        String jpql = "SELECT ta FROM TeachingActivity ta WHERE 1=1 ";
        Map<String, Object> paramsMap = new HashMap<>();

        if (criteria.hour != null) {
            jpql += "  AND ta.timeSlot.startHour = :hour ";
            paramsMap.put("hour", criteria.hour);
        }

        if (criteria.room != null) {
            jpql += "  AND UPPER(ta.timeSlot.roomId) LIKE '%' || UPPER(:room) || '%' ";
            paramsMap.put("room", criteria.room);
        }

        TypedQuery<TeachingActivity> query = em.createQuery(jpql, TeachingActivity.class);
        for (String paramName : paramsMap.keySet()) {
            query.setParameter(paramName, paramsMap.get(paramName));
        }
        return query.getResultList();
    }
}
