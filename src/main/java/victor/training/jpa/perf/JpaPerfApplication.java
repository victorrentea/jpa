package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.perf.entity.Country;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.repo.CountryRepo;
import victor.training.jpa.perf.repo.PostRepo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
@RestController
@RequestMapping
public class JpaPerfApplication {
   public static void main(String[] args) {
      new SpringApplicationBuilder(JpaPerfApplication.class)
          .profiles("insertDummyData")
          .run(args);
   }

   @Autowired
   private CountryRepo countryRepo;
   @GetMapping("/country")
   public List<Country> getAllCountries() {
      return countryRepo.findAll();
   }


   @Autowired
   private PostRepo postRepo;

   // TODO in interface I need title + author name + number of comments
   @GetMapping
   @Transactional
   public Page<PostDto> query() {
//      Page<Post> page = postRepo.findByTitleLike("%os%", PageRequest.of(0, 20));
      Page<Long> idPage = postRepo.findIdByTitleLike("%os%",
          PageRequest.of(0, 20));

      Map<Long, Post> postsAsMap = postRepo.fetchManyWithChildren(idPage.getContent())
          .stream().collect(Collectors.toMap(Post::getId, Function.identity()));

      Page<Post> page = idPage.map(postsAsMap::get);

      log.info("Returning data " + page);
      return page.map(entity -> new PostDto(entity));
   }
}
class PostDto {
   public String title;
   public String author;
   public int commentCount;
   public PostDto(Post entity) {
      title = entity.getTitle();
      author = entity.getAuthor().getUsername();
      commentCount = entity.getComments().size();
   }
}
