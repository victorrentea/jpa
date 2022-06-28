package victor.training.jpa.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import victor.training.jpa.app.util.TestDBConnectionInitializer;

@SpringBootTest 
@ContextConfiguration(initializers = TestDBConnectionInitializer.class) // check the DB is reachable
public class AppFlowTest {
    
    @Test
    void explore() {
        
    }
}
