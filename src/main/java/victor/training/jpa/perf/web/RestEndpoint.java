package victor.training.jpa.perf.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.repo.PostRepo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RestController
@RequestMapping
public class RestEndpoint {
   @Autowired
   private PostRepo postRepo;

   // TODO in interface I need title + author name + number of comments
   @GetMapping
   @Transactional
   public Page<Post> query() {
//      Page<Post> page = postRepo.findByTitleLike("%os%", PageRequest.of(0, 20));

      // TODO delete below
      Page<Long> idPage = postRepo.findIdsByTitleLike("%os%", PageRequest.of(0, 20));

      Set<Post> posts = postRepo.fetchForSearch(idPage.toList());
      Map<Long, Post> idToPost = posts.stream().collect(toMap(Post::getId, Function.identity()));

      Page<Post> page = idPage.map(idToPost::get);

      log.info("Returning data " + page);

      return page;
   }
}

