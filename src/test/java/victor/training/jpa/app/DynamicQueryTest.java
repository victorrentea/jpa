package victor.training.jpa.app;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.repo.TeacherRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class DynamicQueryTest {
   private TeacherSearchCriteria searchCriteria = new TeacherSearchCriteria();

   @Autowired
   private TeacherRepo teacherRepo;
   @Test
   public void byName() {
      teacherRepo.save(new Teacher().setName("Victor"));

      searchCriteria.name = "Victor";
      assertThat(teacherRepo.search(searchCriteria)).hasSize(1);

      searchCriteria.name = "IC";
      assertThat(teacherRepo.search(searchCriteria)).hasSize(1);

      searchCriteria.name = "OTHER";
      assertThat(teacherRepo.search(searchCriteria)).isEmpty();
   }
   @Test
   public void byGrade() {
      teacherRepo.save(new Teacher().setGrade(Grade.PROFESSOR));

      searchCriteria.grade = Grade.PROFESSOR;
      assertThat(teacherRepo.search(searchCriteria)).hasSize(1);

      searchCriteria.grade = Grade.LECTURER;
      assertThat(teacherRepo.search(searchCriteria)).isEmpty();
   }
}
