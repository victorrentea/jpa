package victor.training.jpa.app.common.data;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class EntityRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
    extends JpaRepositoryFactoryBean<R, T, ID> {
	
//    public EntityRepositoryFactoryBean(JpaEntityInformation jpaEntityInformation , EntityManager em) {
//        super();
//        setEntityManager(em);
//    }
//    
	
    public EntityRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@SuppressWarnings("rawtypes")
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new EntityRepositoryFactory(em);
    }
    
    private static class EntityRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
        public EntityRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation<T, ?> entityInformation = (JpaEntityInformation<T, ?>) getEntityInformation(information.getDomainType());
            return new EntityRepositoryImpl<T, ID>( entityInformation, entityManager);
        }
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return EntityRepositoryImpl.class;
        }
        
    }
    
}
