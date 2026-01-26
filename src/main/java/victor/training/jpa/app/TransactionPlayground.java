package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.TeacherRepo;

import jakarta.persistence.EntityManager;

import static org.springframework.transaction.event.TransactionPhase.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final TeacherRepo repo;
    private final AltService altService;

    public void firstTransaction() {
        log.debug("Function Begin");
        altService.atomic(); // adnotarea @Transactional nu merge pe apel local (pe this.)
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");
        altService.atomic();
        repo.save(new Teacher("Al treilea, atomic si el cu fratii"));
    }
}
@Slf4j
@RequiredArgsConstructor
@Service
class AltService {
    private final TeacherRepo repo;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void atomic() {
        repo.save(new Teacher().setName("John2"));
        repo.save(new Teacher().setName("Fratele"/*.repeat(1000)*/));
        // obigatoriu sa fii intr-o tranzactie deschisa
        applicationEventPublisher.publishEvent(new MesajDeTrimis("notificare"));
    }
    record MesajDeTrimis(String ce) {}

//    @Async//😱😱
    @TransactionalEventListener(phase = AFTER_COMMIT) // eg notificari
//    @TransactionalEventListener(phase = AFTER_ROLLBACK) // anunti erori/compensari
//    @TransactionalEventListener(phase = AFTER_COMPLETION) //  cleanup stergi fisiere temp...
    public void method(MesajDeTrimis event) {
        log.info("ws:,API call, kafka Δt mare: " + event.ce);
    }
}