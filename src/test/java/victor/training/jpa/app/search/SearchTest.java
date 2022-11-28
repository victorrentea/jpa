package victor.training.jpa.app.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.CourseActivity;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.repo.TeacherRepo;
import victor.training.jpa.app.repo.TeacherSearchRepo;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
abstract class AbstractSearchTestBase {

   @Autowired
   TeacherRepo teacherRepo;
   @Autowired
   TeacherSearchRepo searchRepo;

   TeacherSearchCriteria criteria = new TeacherSearchCriteria();
   private Teacher teacher = new Teacher();

   protected abstract List<Teacher> search();

   @BeforeEach
   final void before() {
      teacherRepo.save(teacher);
   }

   @Order(1)
   @Test
   void byName() {
      teacher.setName("John");

      assertThat(search()).hasSize(1);

      criteria.name = "John";
      assertThat(search()).hasSize(1);

      criteria.name = "o";
      assertThat(search()).hasSize(1);

      criteria.name = "H";
      assertThat(search()).hasSize(1);

      criteria.name = "Other";
      assertThat(search()).hasSize(0);
   }

   @Order(2)
   @Test
   void byGrade() {
      teacher.setGrade(Grade.PROFESSOR);

      assertThat(search()).hasSize(1);

      criteria.grade = Grade.PROFESSOR;
      assertThat(search()).hasSize(1);

      criteria.grade = Grade.ASSISTANT;
      assertThat(search()).hasSize(0);
   }

   @Autowired
   EntityManager entityManager;

   @Order(3)
   @Test
   void teachingCourses() {
      assertThat(search()).hasSize(1);

      criteria.teachingCourses = true;
      assertThat(search()).hasSize(0);

      CourseActivity course = new CourseActivity();
      course.getTeachers().add(teacher);
      entityManager.persist(course);
      assertThat(search()).hasSize(1);
   }
}

class JpqlConcat extends AbstractSearchTestBase {
   protected List<Teacher> search() {
      return searchRepo.jpqlConcat(criteria);
   }
}

class CriteriaAPI extends AbstractSearchTestBase {
   protected List<Teacher> search() {
      return searchRepo.criteriaApi(criteria);
   }
}

class Specification extends AbstractSearchTestBase {
   protected List<Teacher> search() {
      return searchRepo.specifications(criteria);
   }
}

class QueryDSL extends AbstractSearchTestBase {
   protected List<Teacher> search() {
      return searchRepo.queryDSL(criteria);
   }
}

class FixedJpql extends AbstractSearchTestBase {
   protected List<Teacher> search() {
      return teacherRepo.searchFixedJqpl(criteria.name, criteria.grade, criteria.teachingCourses);
   }
}

class FixedJpqlSpel extends AbstractSearchTestBase {
   protected List<Teacher> search() {
      return teacherRepo.searchFixedJqplSpel(criteria);
   }
}
