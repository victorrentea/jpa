package victor.training.jpa.perf.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("insertDummyData")
public class InsertDummyData implements CommandLineRunner {
   @Autowired
   private JdbcTemplate jdbc;

   @Override
   public void run(String... args) throws Exception {
      log.warn("INSERTING data ...");
      jdbc.update("INSERT INTO USERS(ID, USERNAME) SELECT X, 'User' || X  FROM SYSTEM_RANGE(1, 10)");
      jdbc.update("INSERT INTO POST(ID, TITLE, USER_ID) SELECT X, 'Post ' || X, 1 + MOD(X,10)  FROM SYSTEM_RANGE(1, 1000)");
      jdbc.update("INSERT INTO COMMENTS(ID, TITLE, POST_ID, USER_ID) SELECT X,         'Comment' || X || '-1', X, 1 + MOD(X,10) FROM SYSTEM_RANGE(1, 1000)");
      jdbc.update("INSERT INTO COMMENTS(ID, TITLE, POST_ID, USER_ID) SELECT X + 1000,  'Comment' || X || '-2', X, 1 + MOD(X,10) FROM SYSTEM_RANGE(1, 1000)");
      log.info("DONE");
   }
}
