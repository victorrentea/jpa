package victor.training.jpa.app.repo;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.entity.Teacher.Grade;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchResult;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static victor.training.jpa.app.entity.QCourseActivity.courseActivity;
import static victor.training.jpa.app.repo.TeacherSpecifications.*;

@Repository
public class TeacherSearchRepo {
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private TeacherRepo teacherRepo;

  public List<Teacher> jpqlConcat(TeacherSearchCriteria searchCriteria) { // TODO query directly TeacherSearchResult objects
    List<String> jpqlParts = new ArrayList<>();
    jpqlParts.add("SELECT t FROM Teacher t WHERE 1=1");
    Map<String, Object> params = new HashMap<>();

    if (searchCriteria.name != null) {
      // concatenating JQPL is bad because:
//      jpqlString += "AND UPPER(t.name)..." // no space -> invalid syntax => fix with a list<String> joined at the end
//      jpqlString += "AND UPPER(t.nme)..." // typos in the syntax
//      jpqlParts.add("AND UPPER(t.name) LIKE UPPER('%' || "+searchCriteria.name+" || '%')"); // injection prone
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
      // Criteria flavor#1 - field names mentioned as strings < the easiest way, bun prone to rename fields
//      predicates.add(cb.equal(root.get("grade"), searchCriteria.grade));// without metamodel

      // Criteria flavor#2 - fields referenced from a generated metamodel kept in sync with JPA @Entity model
      Predicate gradeSpec = createGradeSpec(searchCriteria.grade)
              .toPredicate(root, criteriaQuery, cb); // spring does this for you when using Specifications
      predicates.add(gradeSpec);
    }

    if (searchCriteria.name != null) {
      Predicate nameSpec = createNameSpec(cb, criteriaQuery, root, searchCriteria.name);
      predicates.add(nameSpec);
    }

//    jpqlParts.add("AND EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id)");
// vs :
    if (searchCriteria.teachingCourses) {
      // people typically debug this by looking at the generated SQL query
      // if you ever want to do any change in this subquery you'll call a "sick day"
      // => you will definetely write a unit test for your change
      // random trying changes (SO + baeldung.com + vladmihalcea.com in the background) => inspecting the SQL
      // you will attempt 7-10 such queries => trying them with a unit test
      Predicate isTeacherSpec = createTeachesCoursesSpec(cb, criteriaQuery, root);

      predicates.add(isTeacherSpec);
    }
    // Exception on the way: java.lang.IllegalStateException: No explicit selection and an implicit one could not be determined

    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));

    criteriaQuery.orderBy(cb.asc(root.get(searchCriteria.orderBy)));

    TypedQuery<Teacher> query = entityManager.createQuery(criteriaQuery);
    query.setFirstResult(searchCriteria.pageIndex * searchCriteria.pageSize);
    query.setMaxResults(searchCriteria.pageSize);

    return query.getResultList();
  }

  private static Predicate createTeachesCoursesSpec(CriteriaBuilder cb, CriteriaQuery<Teacher> criteriaQuery, Root<Teacher> root) {
    Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
    Root<CourseActivity> subqueryRoot = subquery.from(CourseActivity.class);
    SetJoin<CourseActivity, Teacher> join = subqueryRoot.join(CourseActivity_.teachers);
    subquery.where(cb.equal(root.get(Teacher_.id), join.get(Teacher_.id)));
    Predicate isTeacherSpec = cb.exists(subquery.select(cb.literal(1)));
    return isTeacherSpec;
  }

  private static Predicate createNameSpec(CriteriaBuilder cb, CriteriaQuery<Teacher> criteriaQuery, Root<Teacher> root, String name) {
    return cb.like(cb.upper(root.get(Teacher_.name)), "%" + name.toUpperCase() + "%");
  }

  private static Specification<Teacher> createGradeSpec(Grade grade) {
    return (root, query, cb) -> cb.equal(root.get(Teacher_.grade), grade);
  }

  public List<Teacher> specifications(TeacherSearchCriteria searchCriteria, Pageable pageRequest) {
    Specification<Teacher> spec = all();
    if (searchCriteria.name != null) {
      // the end result:
      spec = spec.and(hasNameLike(searchCriteria.name));

      // #1 instead of 12 @Query method you could 'mix and match' in code the WHERE clause using this technique
      // #2 if you allow the USER in UI to "construct" the WHERE clause => you could map the Dto stuff to such Specifications
    }
    if (searchCriteria.grade != null) {
      spec = spec.and(hasGrade(searchCriteria.grade));
    }
    if (searchCriteria.teachingCourses) {
      spec = spec.and(teachingCourses());
    }
    // xtra: pagination
    return teacherRepo.findAll(spec, pageRequest).getContent();
  }

  // is a different framework on top of JPA
  public List<Teacher> queryDSL(TeacherSearchCriteria searchCriteria) {
    JPAQuery<?> query = new JPAQuery<Void>(entityManager);

    QTeacher teacher = QTeacher.teacher; // also uses a generated metamodel


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

      // "AND EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id)");
      predicates.add(new JPAQuery<Integer>()
              .select(Expressions.constant(1))
              .from(courseActivity)
              .join(courseActivity.teachers, tt)
              .where(tt.id.eq(teacher.id)).exists());
    }

    return query.select(teacher)
            .from(teacher)
            .where(predicates.toArray(new com.querydsl.core.types.Predicate[0]))
            .orderBy(Expressions.stringPath(teacher, searchCriteria.orderBy).asc())
            .offset((long) searchCriteria.pageSize * searchCriteria.pageIndex)
            .limit(searchCriteria.pageSize)
            .fetchAll()
            .fetch();
  }
}
