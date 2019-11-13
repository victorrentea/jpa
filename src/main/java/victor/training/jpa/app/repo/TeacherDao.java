package victor.training.jpa.app.repo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.web.TeacherSearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherDao {
    @PersistenceContext
    private EntityManager em;
    public List<Teacher> search(TeacherSearchCriteria criteria) {
        String jpql = "SELECT t FROM Teacher t WHERE 1=1 ";
        Map<String, Object> paramMap = new HashMap<>();

        if (StringUtils.isNotBlank(criteria.namePart)) {
            jpql += " AND UPPER(t.name) LIKE UPPER(:name) ";
            paramMap.put("name","%" + criteria.namePart + "%");
        }
        if (criteria.grade != null) {
            jpql += " AND t.grade = :grade ";
            paramMap.put("grade", criteria.grade);
        }

        System.out.println("jpql: " + jpql);
        TypedQuery<Teacher> query = em.createQuery(jpql, Teacher.class);
        for (String param : paramMap.keySet()) {
            query.setParameter(param, paramMap.get(param));
        }
        return query.getResultList();
    }
    
}
