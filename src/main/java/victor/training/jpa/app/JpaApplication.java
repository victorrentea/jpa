package victor.training.jpa.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import victor.training.jpa.app.common.CustomJpaRepositoryFactoryBean;
import victor.training.jpa.app.util.TestDBConnectionAndDropAllInitializer;

import java.io.IOException;
import java.sql.SQLException;

//@EnableTransactionManagement//(mode = AdviceMode.ASPECTJ) // enables @Transactional to work for local method call (not a good idea!)
// -javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableJpaAuditing
@Slf4j
public class JpaApplication {

	@Autowired
	private TransactionPlayground transactionPlayground;
	@Autowired
	private MergePlayground mergePlayground;
	@Autowired
	private LobPlayground lobPlayground;


	@EventListener(ContextRefreshedEvent.class)
	public void onStartup() throws IOException, SQLException {
//		log.debug(">>>>>>>>>> Running Playground code... <<<<<<<<<<<<");
//		log.debug(" ========= FIRST TRANSACTION ========== ");
////		transactionPlayground.firstTransaction();
//		transactionPlayground.jpa1();
////		transactionPlayground.otherMethod();
//		log.debug(" ========= SECOND TRANSACTION ========== ");
////		transactionPlayground.secondTransaction(e);
//		transactionPlayground.jpa2();
//		transactionPlayground.jpa3();
//		log.debug(" ========= END ========== ");

		log.debug("==== Merge:Persist init ====");
		mergePlayground.persistInitialData();
		log.debug("==== Merge:READ (user clicked open edit screen) ====");
		String dataFromServer = mergePlayground.readFromBackend("client1");
		log.debug("==== Merge:WRITE1 (user clicked SAVE in edit screen ====");
		mergePlayground.client1(dataFromServer);
		mergePlayground.client1(dataFromServer);
		log.debug("==== Merge:final data in DB ====");
		mergePlayground.printFinalData();

//		log.debug("Uploading file...");
//		var uploadId = lobPlayground.uploadLargeClob();
//		log.debug("Downloading file...");
//		lobPlayground.downloadLargeClob(uploadId);
//		log.debug("Accessing Teacher Details file...");
//		lobPlayground.getTeacherAutoLoadsTheClob();

		log.debug(">>>>>>>>>> Playground Finished <<<<<<<<<<<<");

	}


//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(JpaApplication.class)
				.listeners(new TestDBConnectionAndDropAllInitializer())
				.run(args);
	}
}
