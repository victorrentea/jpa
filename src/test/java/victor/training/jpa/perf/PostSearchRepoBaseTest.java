//package victor.training.jpa.perf;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import victor.training.jpa.app.domain.entity.CourseActivity;
//import victor.training.jpa.app.domain.entity.Teacher;
//import victor.training.jpa.app.domain.entity.Teacher.Grade;
//import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
//import victor.training.jpa.perf.dto.PostSearchCriteria;
//import victor.training.jpa.perf.entity.Comment;
//import victor.training.jpa.perf.entity.Post;
//import victor.training.jpa.perf.entity.Post.PostType;
//import victor.training.jpa.perf.repo.PostRepo;
//import victor.training.jpa.perf.repo.TeacherRepo;
//import victor.training.jpa.perf.repo.TeacherSearchRepo;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional
//@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
//abstract class PostSearchRepoBaseTest {
//
//   @Autowired
//   PostRepo teacherRepo;
//   @Autowired
//   TeacherSearchRepo searchRepo;
//
//   PostSearchCriteria criteria = new PostSearchCriteria();
//   private Post post = new Post();
//
//   protected abstract List<Post> search();
//
//   @BeforeEach
//   final void before() {
//      teacherRepo.save(post);
//   }
//
//   @Order(1)
//   @Test
//   void byTitle() {
//      post.setTitle("John");
//
//      assertThat(search()).hasSize(1);
//
//      criteria.title = "John";
//      assertThat(search()).hasSize(1);
//
//      criteria.title = "o";
//      assertThat(search()).hasSize(1);
//
//      criteria.title = "H";
//      assertThat(search()).hasSize(1);
//
//      criteria.title = "Other";
//      assertThat(search()).hasSize(0);
//   }
//
//   @Order(2)
//   @Test
//   void byType() {
//      post.setPostType(PostType.BEST_PRACTICES);
//
//      assertThat(search()).hasSize(1);
//
//      criteria.postType = PostType.BEST_PRACTICES;
//      assertThat(search()).hasSize(1);
//
//      criteria.postType = PostType.PHILOSOPHY;
//      assertThat(search()).hasSize(0);
//   }
//
//   @Order(3)
//   @Test
//   void havingComments() {
//      assertThat(search()).hasSize(1);
//
//      criteria.havingComments = true;
//      assertThat(search()).hasSize(0);
//
//      post.addComment(new Comment("Wonderful"));
//      assertThat(search()).hasSize(1);
//   }
//}
//
//@Order(1)
//class JpqlConcat extends PostSearchRepoBaseTest {
//   protected List<Post> search() {
//      return searchRepo.jpqlConcat(criteria);
//   }
//}
//
//@Order(2)
//class CriteriaAPI extends PostSearchRepoBaseTest {
//   protected List<Post> search() {
//      return searchRepo.criteriaApi(criteria);
//   }
//}
//
//@Order(3)
//class Specification extends PostSearchRepoBaseTest {
//   protected List<Post> search() {
//      return searchRepo.specifications(criteria);
//   }
//}
//
//@Order(4)
//class QueryDSL extends PostSearchRepoBaseTest {
//   protected List<Post> search() {
//      return searchRepo.queryDSL(criteria);
//   }
//}