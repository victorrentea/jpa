package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Component
@Profile("insertDummyData")
public class InsertDummyData implements CommandLineRunner {
   @Autowired
   private JdbcTemplate jdbc;
   @Autowired
   private EntityManager entityManager;

   @Override
   @Transactional
   public void run(String... args) throws Exception {
      log.warn("INSERTING data ...");
      jdbc.update("INSERT INTO COUNTRY(ID, NAME) SELECT X, 'Country' || X  FROM SYSTEM_RANGE(1, 10)");
      jdbc.update("INSERT INTO USER(ID, USERNAME) SELECT X, 'User' || X  FROM SYSTEM_RANGE(1, 10)");
      jdbc.update("INSERT INTO POST(ID, TITLE, AUTHOR_ID ) SELECT X, 'Post ' || X, 1 + MOD(X,10)  FROM SYSTEM_RANGE(1, 1000)");
      jdbc.update("INSERT INTO COMMENTS(ID, TITLE, POST_ID, USER_ID) SELECT X,         'Comment' || X || '-1', X, 1 + MOD(X,10) FROM SYSTEM_RANGE(1, 1000)");
      jdbc.update("INSERT INTO COMMENTS(ID, TITLE, POST_ID, USER_ID) SELECT X + 1000,  'Comment' || X || '-2', X, 1 + MOD(X,10) FROM SYSTEM_RANGE(1, 1000)");
      log.info("DONE");
   }
}
