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
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.repo.PostRepo;

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
   private PostRepo postRepo;

   // TODO in interface I need title + author name + number of comments
   @GetMapping
   @Transactional
   public Page<Post> query() {
      Page<Post> page = postRepo.findByTitleLike("%os%", PageRequest.of(0, 20));

      log.info("Returning data " + page);
      return page;
   }
}
