package victor.training.jpa.app.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.repo.TeacherRepo;

import java.util.List;

@SpringBootTest
public class SearchTest {
   @Autowired
   TeacherRepo repo;

   private TeacherSearchCriteria criteria = new TeacherSearchCriteria();

   @Test
   public void search() {
      repo.save(new Teacher());
      List<Teacher> results = repo.search(criteria);

      Assertions.assertThat(results).hasSize(1);

   }
}
