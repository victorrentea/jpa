package victor.training.jpa.app.repo;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static victor.training.jpa.app.entity.QCourseActivity.courseActivity;

@Repository
public class TeacherSearchRepo {
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private TeacherRepo teacherRepo;

  public List<Teacher> getAllTeachersForYear(long yearId) {
    // JPQL equivalent of an UNION between teachers
    return entityManager.createQuery(
                    "SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE "
                    + "a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId) "
                    + "OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)",
                    Teacher.class)
            .setParameter("yearId", yearId)
            .getResultList();
  }

  public List<Teacher> jpqlConcat(TeacherSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
    List<String> jpqlParts = new ArrayList<>();
    jpqlParts.add("SELECT t FROM Teacher t WHERE 1=1");
    Map<String, Object> params = new HashMap<>();

    if (searchCriteria.name != null) {
      jpqlParts.add("AND UPPER(t.name) LIKE UPPER('%' || :name || '%')");
      params.put("name", searchCriteria.name);
    }

    if (searchCriteria.grade != null) {
      jpqlParts.add("AND t.grade = :grade");
      params.put("grade", searchCriteria.grade);
    }
    if (searchCriteria.teachingCourses) {
      jpqlParts.add("AND EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id)");
    }

    jpqlParts.add("ORDER BY " + searchCriteria.orderBy);

    TypedQuery<Teacher> query = entityManager.createQuery(String.join("\n", jpqlParts), Teacher.class);
    for (String param : params.keySet()) {
      query.setParameter(param, params.get(param));
    }
    query.setFirstResult(searchCriteria.pageIndex * searchCriteria.pageSize);
    query.setMaxResults(searchCriteria.pageSize);
    return query.getResultList();
  }

  public List<TeacherSearchResult> searchProjectionUsingCriteriaMetamodel() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<TeacherSearchResult> criteriaQuery = cb.createQuery(TeacherSearchResult.class);
    Root<Teacher> root = criteriaQuery.from(Teacher.class);
    criteriaQuery.select(cb.construct(TeacherSearchResult.class,
            root.get(Teacher_.id), root.get(Teacher_.name), root.get(Teacher_.grade)));
    TypedQuery<TeacherSearchResult> query = entityManager.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public List<Teacher> criteriaApi(TeacherSearchCriteria searchCriteria) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Teacher> criteriaQuery = cb.createQuery(Teacher.class);
    Root<Teacher> root = criteriaQuery.from(Teacher.class);

    List<Predicate> predicates = new ArrayList<>();

    if (searchCriteria.grade != null) {
      // TODO extract Spring Specifications starting from cb.equal ...
      predicates.add(cb.equal(root.get("grade"), searchCriteria.grade));// without metamodel
      //         predicates.add(cb.equal(root.get(Teacher_.grade), searchCriteria.grade));
    }

    if (searchCriteria.name != null) {
      predicates.add(cb.like(cb.upper(root.get(Teacher_.name)), "%" + searchCriteria.name.toUpperCase() + "%"));
    }

    if (searchCriteria.teachingCourses) {
      Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
      Root<CourseActivity> subqueryRoot = subquery.from(CourseActivity.class);
      SetJoin<CourseActivity, Teacher> join = subqueryRoot.join(CourseActivity_.teachers);
      subquery.where(cb.equal(root.get(Teacher_.id), join.get(Teacher_.id)));
      predicates.add(cb.exists(subquery.select(cb.literal(1))));
    }
    // Exception on the way: java.lang.IllegalStateException: No explicit selection and an implicit one could not be determined

    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));

    criteriaQuery.orderBy(cb.asc(root.get(searchCriteria.orderBy)));

    TypedQuery<Teacher> query = entityManager.createQuery(criteriaQuery);
    query.setFirstResult(searchCriteria.pageIndex * searchCriteria.pageSize);
    query.setMaxResults(searchCriteria.pageSize);

    return query.getResultList();
  }

  public List<Teacher> specifications(TeacherSearchCriteria searchCriteria, Pageable pageRequest) {
    Specification<Teacher> spec = TeacherSpecifications.all();
    if (searchCriteria.name != null) {
      spec = spec.and(TeacherSpecifications.hasNameLike(searchCriteria.name));
    }
    if (searchCriteria.grade != null) {
      spec = spec.and(TeacherSpecifications.hasGrade(searchCriteria.grade));
    }
    if (searchCriteria.teachingCourses) {
      spec = spec.and(TeacherSpecifications.teachingCourses());
    }
    // xtra: pagination
    return teacherRepo.findAll(spec, pageRequest).getContent();
  }

  public List<Teacher> queryDSL(TeacherSearchCriteria searchCriteria) {
    JPAQuery<?> query = new JPAQuery<Void>(entityManager);

    QTeacher teacher = QTeacher.teacher;
    JPAQuery<Teacher> outerQuery = query.select(teacher)
            .from(teacher);


    List<com.querydsl.core.types.Predicate> predicates = new ArrayList<>();
    if (searchCriteria.grade != null) {
      predicates.add(teacher.grade.eq(searchCriteria.grade));
    }
    if (searchCriteria.name != null) {
      predicates.add(teacher.name.upper()
              .like("%" + searchCriteria.name.toUpperCase() + "%"));
    }
    if (searchCriteria.teachingCourses) {
      QTeacher tt = new QTeacher("tt");

      predicates.add(new JPAQuery<Integer>()
              .select(Expressions.constant(1))
              .from(courseActivity)
              .join(courseActivity.teachers, tt)
              .where(tt.id.eq(teacher.id)).exists());
    }

    return outerQuery
            .where(predicates.toArray(new com.querydsl.core.types.Predicate[0]))
            .orderBy(Expressions.stringPath(teacher, searchCriteria.orderBy).asc())
            .offset((long) searchCriteria.pageSize * searchCriteria.pageIndex)
            .limit(searchCriteria.pageSize)
            .fetchAll()
            .fetch();
  }
}
