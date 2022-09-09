package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.repo.ErrorLogRepo;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    public static final ThreadLocal<String> magie = new ThreadLocal<>();
    private final ErrorLogRepo repo;
    private final Other other;

    @Transactional
    public void firstTransaction() throws IOException {
        log.debug("Function Begin");
        repo.save(new ErrorLog("mesaj"));
        if (true) {
            throw new IOException("Orice exceptie  (validate, ... conn timeout)");
        }
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


}
