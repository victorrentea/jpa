package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.repo.ErrorLogRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    public static final ThreadLocal<String> magie = new ThreadLocal<>();
    private final ErrorLogRepo repo;
    private final Other other;

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");
        repo.save(new ErrorLog("mesaj"));
        magie.set("ceva");
        other.metoda();

        log.debug("Function End");
    }

    @Transactional
    public void secondTransaction() {


    }

}

@Slf4j
@RequiredArgsConstructor
@Service
class Other {
    private final ErrorLogRepo repo;

    public void metoda() {
        System.out.println(TransactionPlayground.magie.get());
        //print thread localuri se propaga metadate intre "inceputul fluxului" si metode chemate mai jos
        repo.save(new ErrorLog(null));

         // prin aceste ThreadLOcal se mai propaga:
        // - JDBC COnnection + @Transactional
        // - SecurityCOntextHolder
        // - Logback MDC (Apache Sleuth) /
    }
}
