package victor.training.jpa.perf;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.perf.repo.PostRepo;

import javax.persistence.EntityManager;

import static java.lang.System.currentTimeMillis;
import static victor.training.jpa.perf.helper.PerformanceUtil.printUsedHeap;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
public class StreamingQuery  {
   @Autowired
   private EntityManager entityManager;
   @Autowired
   private PostRepo postRepo;
   @Autowired
   private JdbcTemplate jdbc;


   @BeforeEach
   final void before() {
      jdbc.update("INSERT INTO POST(ID, TITLE) SELECT X, 'Post ' || X  FROM SYSTEM_RANGE(1, 100000)");
      System.out.println("Data inserted");
   }
   @Test
   void exportData() {
      System.out.println("COUNT: " + postRepo.count());

      long t0 = currentTimeMillis();

      long n = postRepo.streamAll()
          .peek(p -> { if (p.getId() % 10000 == 0) printUsedHeap("At #" + p.getId()); })
          .count();

      System.out.println("Found " + n);
      long t1 = currentTimeMillis();
      System.out.println("Took " + (t1-t0));
   }
}
