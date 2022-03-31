package victor.training.jpa.perf;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
//@Transactional
//@Rollback(false)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GeneratedUUIDTest {
    private static final Logger log = LoggerFactory.getLogger(GeneratedUUIDTest.class);

    @Autowired
    private EntityManager em;
    @Autowired
    private GeneratedUUIDEntityRepo repo;

    @Test
    public void assignIdentifiers() {
        repo.save(new GeneratedUUIDEntity());
    }
}
