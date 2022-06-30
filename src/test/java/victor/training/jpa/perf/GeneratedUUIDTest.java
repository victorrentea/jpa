package victor.training.jpa.perf;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import victor.training.jpa.app.CaptureSystemOutput;
import victor.training.jpa.app.CaptureSystemOutput.OutputCapture;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Transactional
//@Rollback(false)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GeneratedUUIDTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private GeneratedUUIDEntityRepo repo;

    @Test
    @CaptureSystemOutput
    public void assignIdentifiers(OutputCapture capture) {
        repo.save(new GeneratedUUIDEntity());

        // uncomment bellow and move to option 2 in prod code to fix
//        assertThat(capture.toString()).doesNotContainIgnoringCase("SELECT");
    }
}
