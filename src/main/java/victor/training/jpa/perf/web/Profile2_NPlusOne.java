package victor.training.jpa.perf.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.perf.entity.Post;
import victor.training.jpa.perf.repo.PostRepo;

@Slf4j
@RestController
@RequestMapping("profile/nplus1")
public class Profile2_NPlusOne {
   @Autowired
   private PostRepo repo;

   @Component
   @Profile("massiveData")
   public static class MassiveDataInsert implements CommandLineRunner {
      @Autowired
      private JdbcTemplate jdbc;
      @Override
      public void run(String... args) throws Exception {
         log.warn("INSERTING data ...");
         jdbc.update("INSERT INTO COUNTRY(ID, NAME) SELECT X, 'Country ' || X  FROM SYSTEM_RANGE(1, 20)");
         jdbc.update("INSERT INTO PARENT(ID, NAME, COUNTRY_ID) SELECT X, 'Parent' || X, 1 + MOD(X,20)  FROM SYSTEM_RANGE(1, 1000)");

         // jdbc.update("INSERT INTO PARENT(ID, NAME) SELECT X, 'Parent ' || X FROM SYSTEM_RANGE(1, 1000)");
         jdbc.update("INSERT INTO CHILD(ID, NAME, PARENT_ID) SELECT X, 'Child' || X || '-1',X FROM SYSTEM_RANGE(1, 1000)");
         jdbc.update("INSERT INTO CHILD(ID, NAME, PARENT_ID) SELECT X + 1000, 'Child' || X || '-2', X FROM SYSTEM_RANGE(1, 1000)");
         log.info("DONE");
      }
   }

   @GetMapping
   @Transactional
   public Page<Post> query() {
      Page<Post> parentPage = repo.findByTitleLike("%ar%", PageRequest.of(0, 20));
      log.info("Returning");
      return parentPage;

//      Page<Long> idPage = repo.findByNameLike("%ar%", PageRequest.of(0, 10));
//      List<Long> parentIds = idPage.getContent();
//      Map<Long, Parent> parents = repo.findParentsWithChildren(parentIds).stream().collect(toMap(Parent::getId, identity()));
//      return idPage.map(parents::get);
   }
}

