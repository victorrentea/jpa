package victor.training.jpa.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import victor.training.jpa.app.common.CustomJpaRepositoryFactoryBean;
import victor.training.jpa.app.util.TestDBConnectionInitializer;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement//(mode = AdviceMode.ASPECTJ) // enables @Transactional to work for local method call (AVOID!)
@Slf4j
//-javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class JpaApplication {

	@Autowired
	private TransactionPlayground transactionPlayground;
	@Autowired
	private MergePlayground mergePlayground;
	@Autowired
	private LobPlayground lobPlayground;


	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) throws IOException, SQLException {
		log.debug(">>>>>>>>>> Running Playground code... <<<<<<<<<<<<");
//		log.debug(" ========= FIRST TRANSACTION ========== ");
//		transactionPlayground.firstTransaction();
//		log.debug(" ========= SECOND TRANSACTION ========== ");
//		transactionPlayground.secondTransaction();
//		log.debug(" ========= END ========== ");

//		log.debug("==== Merge:Persist init ====");
//		mergePlayground.persistInitialData();
//		log.debug("==== Merge:READ (better: 'open edit screen') ====");
//		String dataForClient1 = mergePlayground.readFromBackend("client1");
//		log.debug("==== Merge:WRITE1 (better: 'press SAVE in edit screen' ====");
//		mergePlayground.client1(dataForClient1);

//		log.debug("Uploading file...");
//		lobPlayground.uploadLargeClob();
//		log.debug("Downloading file...");
//		lobPlayground.downloadLargeClob();

		log.debug(">>>>>>>>>> Playground Finished <<<<<<<<<<<<");

	}


//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(JpaApplication.class)
				.listeners(new TestDBConnectionInitializer())
				.run(args);
	}
}
