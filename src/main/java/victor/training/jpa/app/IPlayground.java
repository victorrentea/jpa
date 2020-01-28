package victor.training.jpa.app;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface IPlayground {
    void firstTransaction();
    void secondTransaction();
}
