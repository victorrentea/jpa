package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);


    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");

//        ActivitySearchCriteria criteria; // hm...
    }
}
