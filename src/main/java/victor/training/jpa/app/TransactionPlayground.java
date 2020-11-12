package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
   private final EntityManager entityManager; // JPA
   private final ErrorLogRepo errorLogRepo;
   private final TeacherRepo teacherRepo; // Spring Data JPA

   public void firstTransaction() {
      log.debug("Halo!");
      errorLogRepo.save(new ErrorLog("Spring Data"));
   }

   @Transactional
   public void secondTransaction() {
      log.debug("Halo2!");
      ErrorLog errorLog = errorLogRepo.findById(1L).get();

      errorLog.setMessage("CHANGE");
      log.debug("Is the update sent ABOVE this line ?");
   }
}
