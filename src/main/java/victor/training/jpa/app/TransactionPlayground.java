//package victor.training.jpa.app;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.ehcache.CacheManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import victor.training.jpa.app.domain.entity.ErrorLog;
//import victor.training.jpa.app.domain.entity.Teacher;
//import victor.training.jpa.app.repo.ErrorLogRepo;
//import victor.training.jpa.app.repo.TeacherRepo;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//import java.util.List;
//
//import static java.util.Arrays.asList;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class TransactionPlayground {
//    private final EntityManager em;
//    private final JdbcTemplate jdbc;
//    private final TeacherRepo teacherRepo;
//    private final ErrorLogRepo repo;
//
//    @Transactional
//    public void firstTransaction() {
//        log.debug("Function Begin");
//
//        repo.save(new ErrorLog("Halo!"));
//
//        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");
//        log.debug("Function End");
//    }
//
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void secondTransaction() {
//        log.debug("Halo2!");
//        System.out.println(teacherRepo.findAll());
//    }
//}
