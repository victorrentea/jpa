package victor.training.jpa.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import victor.training.jpa.app.common.data.EntityRepositoryFactoryBean;

import javax.sql.XADataSource;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement/*(mode = AdviceMode.ASPECTJ)*/
@Slf4j
//-javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class JpaApplication {

//	@Autowired
//	private DummyDataCreator dummyDataCreator;



//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}


@Slf4j
@Component
@Profile("!test")
class ProdOnly {
	@Autowired
	private PlatformTransactionManager txm;
	@Autowired
	private TransactionPlayground transactionPlayground;
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug(txm.getClass().getSimpleName());
		log.debug(">>>>>>>>>> Running Transaction Playground code... <<<<<<<<<<<<");
//		dummyDataCreator.persistDummyData();
		log.debug(" ========= FIRST TRANSACTION ========== ");
		transactionPlayground.firstTransaction();
		log.debug(" ========= SECOND TRANSACTION ========== ");
		transactionPlayground.secondTransaction();
		log.debug(" ========= 3 ========== ");
		transactionPlayground.thirdTransaction();
		log.debug(" ========= 4 ========== ");
		transactionPlayground.four();

		log.debug(" ========= END ========== ");
		log.debug("Wow! what a ride. What was that ?!" + transactionPlayground.getClass());
	}
}