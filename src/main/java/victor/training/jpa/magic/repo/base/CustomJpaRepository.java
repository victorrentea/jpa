package victor.training.jpa.magic.repo.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
   /**
    * @param id the PK to lookup
    * @return the Entity. Never null.
    * @throws javax.persistence.EntityNotFoundException when ID not found ind DB
    */
   T findOneById(ID id);
}
