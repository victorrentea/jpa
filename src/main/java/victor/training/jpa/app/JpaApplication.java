package victor.training.jpa.app;

import lombok.extern.slf4j.Slf4j;
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

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement/*(mode = AdviceMode.ASPECTJ)*/
@Slf4j
//-javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class JpaApplication {

	@Autowired
	private DummyDataCreator dummyDataCreator;
	@Autowired
	private TransactionPlayground transactionPlayground;

	@Autowired
	private PlatformTransactionManager txm;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug(txm.getClass().getSimpleName());
		log.debug(">>>>>>>>>> Running Transaction Playground code... <<<<<<<<<<<<");
		dummyDataCreator.persistDummyData();
		log.debug(" ========= FIRST TRANSACTION ========== ");
		transactionPlayground.firstTransaction();
		log.debug(" ========= SECOND TRANSACTION ========== ");
		transactionPlayground.secondTransaction();
		log.debug(" ========= END ========== ");
	}
	

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
