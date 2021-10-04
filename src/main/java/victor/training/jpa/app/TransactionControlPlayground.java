package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionControlPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final AltService altService;

    @Transactional // deschide TX noua pentru ca inca nu exista una deschisa pe th curent
    public void firstTransaction() {
        log.debug("Function Begin");

        repo.save(new ErrorLog("Fara @Tr in jur"));
        altService.metodaDeBiz();

        log.debug("Function End");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}
@Component
@RequiredArgsConstructor
class AltService {
    private final ErrorLogRepo repo;

    @Transactional // prelua Tx deschisa la linia 28
    public void metodaDeBiz() {
        repo.save(new ErrorLog("#sieu"));
    }
}
