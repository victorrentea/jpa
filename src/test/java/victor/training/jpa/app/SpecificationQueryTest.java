package victor.training.jpa.app;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.domain.entity.Teacher_;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Root;
import static javafx.scene.input.KeyCode.T;
import static org.assertj.core.api.Assertions.assertThat;
import static victor.training.jpa.app.TeacherSpec.teacherHasName;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SpecificationQueryTest {
   private TeacherSearchCriteria searchCriteria = new TeacherSearchCriteria();

   @Autowired
   private TeacherRepo teacherRepo;

   @BeforeEach
   public void initialize() {
      assertThat(teacherRepo.count()).isEqualTo(0);
   }
   @Test
   public void byName() {
      teacherRepo.save(new Teacher().setName("Victor"));

      assertThat(teacherRepo.findAll(teacherHasName("Victor"))).hasSize(1);

      assertThat(teacherRepo.findAll(teacherHasName("IC"))).hasSize(1);

//      assertThat(teacherRepo.search(searchCriteria)).isEmpty();
   }
//   @Test
//   public void byGrade() {
//      teacherRepo.save(new Teacher().setGrade(Grade.PROFESSOR));
//
//      searchCriteria.grade = Grade.PROFESSOR;
//      assertThat(teacherRepo.search(searchCriteria)).hasSize(1);
//
//      searchCriteria.grade = Grade.LECTURER;
//      assertThat(teacherRepo.search(searchCriteria)).isEmpty();
//   }


}

class TeacherSpec {
   public static Specification<Teacher> teacherHasName(String name) {
      return new Specification<Teacher>() {
         @Override
         public Predicate toPredicate(javax.persistence.criteria.Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(Teacher_.name)), "%" + name.toUpperCase() + "%");
         }
      };
   }
}