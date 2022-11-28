package victor.training.jpa.app.common;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// look here: extension to JpaRepository interface
@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    
    void detach(T oldRecord);

    T findOneById(ID id);
    
}
