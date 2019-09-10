package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.domain.entity.TimeSlot;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @Autowired
    private EntityManager em;

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
