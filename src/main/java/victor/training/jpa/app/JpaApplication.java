package victor.training.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import victor.training.jpa.app.common.data.EntityRepositoryFactoryBean;
import victor.training.jpa.app.domain.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Clock;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement/*(mode = AdviceMode.ASPECTJ)*/
//-javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class JpaApplication {

//	@Autowired
//	private DummyDataCreator dummyDataCreator;
	@Autowired
	private Playground playground;

	@Autowired
	private PlatformTransactionManager txm;

	@PersistenceContext
	private EntityManager em;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println(txm.getClass());
		System.out.println("Application started. Running playground code...");
//		dummyDataCreator.persistDummyData();
		System.out.println(" ========= FIRST TRANSACTION ========== ");
		playground.firstTransaction();
		System.out.println(" ========= SECOND TRANSACTION ========== ");
		playground.secondTransaction();
		System.out.println("Start..");
//		Teacher teacher = em.find(Teacher.class, 3L); // causes a :
		// org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: victor.training.jpa.app.domain.entity.Teacher.channels, could not initialize proxy - no Session
		//	at org.hibernate.collection.internal.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:582)
		//	at org.hibernate.collection.internal.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:201)
		//	at org.hibernate.collection.internal.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:561)
		//	at org.hibernate.collection.internal.AbstractPersistentCollection.read(AbstractPersistentCollection.java:132)
		//	at org.hibernate.collection.internal.PersistentBag.iterator(PersistentBag.java:277)
		Teacher teacher = em.createNamedQuery("Teacher.fetchChannels",Teacher.class)
				.setParameter("id", 3L)
				.getSingleResult();
		playground.jsp(teacher);
		System.out.println(" ========= 3 TRANSACTION ========== ");
		playground.thirdTransaction();
		System.out.println(" ========= Day 2 - take 1 ========== ");
		playground.day2_take1();
		System.out.println(" ========= END ========== ");
	}

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
