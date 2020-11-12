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
import victor.training.jpa.app.domain.entity.ContactChannel;
import victor.training.jpa.app.domain.entity.ContactChannel.Type;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.SubjectRepo;
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
   private final SubjectRepo subjectRepo;

   @Transactional
   public void firstTransaction() {
      log.debug("Halo!");
      ErrorLog logError = errorLogRepo.save(new ErrorLog("Spring Data"));
      log.debug(logError.getId().toString());

      /// ------------
      Teacher teacher = new Teacher("Tavi");
      ContactChannel contactChannel = new ContactChannel(Type.PERSONAL_PHONE, "10321938193");

      teacher.addChannel(contactChannel);
      teacherRepo.save(teacher);

      Subject subject = new Subject();
      teacher.addHeldSubject(subject);

//      subjectRepo.save(subject);
   }


   @Transactional
   public void secondTransaction() {
//      log.debug("Halo2!");
//      ErrorLog errorLog = errorLogRepo.findById(1L).get();
//      log.debug("Before the change");
//
//      errorLog.setMessage("CHANGE");
//      log.debug(errorLogRepo.findByMessageLike("%HANG%").toString());
//      errorLog.setMessage("BACK");
//      log.debug("Is the update sent ABOVE this line ?");

      // -------
      List<Teacher> teachers = teacherRepo.findAll();
//      for (Teacher teacher : teachers) {
//         log.debug("Teacher {} teaching {}", teacher, teacher.getHeldSubjects());
//      }

      subjectRepo.deleteAll();

      entityManager.detach(teachers.get(0));
      teacherRepo.delete(teachers.get(0));


   }
}
