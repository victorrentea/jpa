package victor.training.jpa.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.CourseActivity;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;
import victor.training.jpa.app.repo.TeacherSearchRepo;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
abstract class TeacherSearchRepoTest {

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


   @Autowired
   protected SubjectRepo subjectRepo;




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

//@Order(1)
class JpqlConcat extends TeacherSearchRepoTest {


   @Test
   void explore() {

      Subject s = new Subject("un nume");
      s.setDescription("desc");
      subjectRepo.save(s);
   }


   protected List<Teacher> search() {
      return searchRepo.jpqlConcat(criteria);
   }
}

//@Order(2)
class CriteriaAPI extends TeacherSearchRepoTest {
   protected List<Teacher> search() {
      return searchRepo.criteriaApi(criteria);
   }
}

//@Order(3)
class Specification extends TeacherSearchRepoTest {
   protected List<Teacher> search() {
      return searchRepo.specifications(criteria);
   }
}

//@Order(4)
class QueryDSL extends TeacherSearchRepoTest {
   protected List<Teacher> search() {
      return searchRepo.queryDSL(criteria);
   }
}