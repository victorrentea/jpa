package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.ErrorTag;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersistMergePlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final AnotherClassToGoThroughProxies anotherClass;


    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");

        log.debug("Function End");
    }

    public void secondTransaction() {
        anotherClass.transaction1();
        anotherClass.transaction2();
    }
}

@Component
@RequiredArgsConstructor
class AnotherClassToGoThroughProxies {

    @Transactional
    public void transaction1() {

    }

    @Transactional
    public void transaction2() {

    }
}