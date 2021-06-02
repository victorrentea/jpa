package victor.training.jpa.magic.repo.base;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

public class CustomJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    protected EntityManager entityManager;
    
    public CustomJpaRepositoryImpl(JpaEntityInformation<T,?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }



	@Override
	public T findOneById(ID id) {
		return findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("No " + getDomainClass().getSimpleName() + " with id " + id));
	}
	
}
