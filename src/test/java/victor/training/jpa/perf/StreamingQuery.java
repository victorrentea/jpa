package victor.training.jpa.perf;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.helper.DatabaseWithLatencyTestBase;
import victor.training.jpa.perf.repo.PostRepo;

import static java.lang.System.currentTimeMillis;


public class StreamingQuery extends DatabaseWithLatencyTestBase {
   @Autowired
   private PostRepo postRepo;
   @Autowired
   private JdbcTemplate jdbc;


   @BeforeEach
   final void before() {
      jdbc.update("INSERT INTO POST(ID, TITLE) SELECT generate_series, 'Post ' || generate_series  FROM generate_series(1, 100000)");
      System.out.println("Data inserted");
   }
   @Test
   @Transactional
   void exportData() {
      System.out.println("COUNT: " + postRepo.count());

      long t0 = currentTimeMillis();
      long n = postRepo.streamAll()
          .count();

      System.out.println("Found " + n);
      long t1 = currentTimeMillis();
      System.out.println("Took " + (t1-t0));
   }
}
