package victor.training.jpa.app.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.facade.dto.TeacherSearchResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SearchTest {
   @Autowired
   TeacherRepo repo;

   private TeacherSearchCriteria criteria = new TeacherSearchCriteria();

   @Test
   public void search() {
      repo.save(new Teacher("a"));
      List<TeacherSearchResult> results = repo.searchCriteriaMetamodel(criteria);
      assertThat(results).hasSize(1);
   }
   @Test
   public void matchByName() {
      repo.save(new Teacher("b"));
      criteria.name = "b";
      List<TeacherSearchResult> results = repo.searchCriteriaMetamodel(criteria);
      assertThat(results).hasSize(1);
   }
   @Test
   public void notMatchByName() {
      repo.save(new Teacher("b"));
      criteria.name = "x";
      List<TeacherSearchResult> results = repo.searchCriteriaMetamodel(criteria);
      assertThat(results).hasSize(0);
   }
//   @Test
//   public void search() {
//      repo.save(new Teacher());
//      List<Teacher> results = repo.search(criteria);
//
//      Assertions.assertThat(results).hasSize(1);
//
//   }
}
