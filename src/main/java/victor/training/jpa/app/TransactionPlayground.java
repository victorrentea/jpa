package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.repo.ErrorLogRepo;

import java.io.IOException;
import java.io.UncheckedIOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    public static final ThreadLocal<String> magie = new ThreadLocal<>();
    private final ErrorLogRepo repo;
    private final Other other;

    @Transactional
    public void firstTransaction()  {
        log.debug("Function Begin");
        try {
            bizAdanc();
        } catch (Exception e) {
            other.saveError(e);
            log.error("SWallow: " + e);
        }
        log.debug("Function End");
    }

    @Transactional
    public void bizAdanc() {
        repo.save(new ErrorLog("BIZ mesaj"));

        if (true) {
            throw new UncheckedIOException(new IOException("Orice exceptie  (validate, ... conn timeout)"));
        }
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

    @Transactional(propagation = Propagation.REQUIRES_NEW) // NU MERGE CA NU TRECE PRIN PROXY!!
    // un apel de metoda in aceeasi clasa (local) NU TRECE PRIN PROXY.
    public void saveError(Exception e) {
        repo.save(new ErrorLog("EROARE VALEU: " + e.getMessage()));
    }


}
