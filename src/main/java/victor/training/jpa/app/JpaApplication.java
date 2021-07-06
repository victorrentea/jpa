package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	private MergePlayground mergePlayground;


	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) throws JsonProcessingException {
		log.debug(">>>>>>>>>> Running Transaction Playground code... <<<<<<<<<<<<");
		dummyDataCreator.persistDummyData();
//		log.debug(" ========= FIRST TRANSACTION ========== ");
//		transactionPlayground.firstTransaction();
//		log.debug(" ========= SECOND TRANSACTION ========== ");
//		transactionPlayground.secondTransaction();
//		log.debug(" ========= END ========== ");

		log.debug("=== Merge:Persist init ===");
		mergePlayground.persistInitialData();
		log.debug("=== Merge:READ ===");
		mergePlayground.readFromDb();
		log.debug("=== Merge:WRITE1 ===");
		mergePlayground.client1();
	}
	

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
