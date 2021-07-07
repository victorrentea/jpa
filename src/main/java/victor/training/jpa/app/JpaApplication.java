package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import victor.training.jpa.app.repo.common.CustomJpaRepositoryFactoryBean;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement/*(mode = AdviceMode.ASPECTJ)*/
@Slf4j
//-javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class JpaApplication {

//	@Autowired
//	private DummyDataCreator dummyDataCreator;
	@Autowired
	private TransactionPlayground transactionPlayground;
	@Autowired
	private MergePlayground mergePlayground;
	@Autowired
	private LobPlayground lobPlayground;

	@Autowired
	private PlatformTransactionManager txm;
	@Autowired
	private PropagationAndExceptions propagationAndExceptions;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) throws IOException, SQLException {
		log.debug(transactionPlayground.getClass().getName());
		log.debug(">>>>>>>>>> Running Transaction Playground code... <<<<<<<<<<<<");


//		propagationAndExceptions.first();

//		log.debug(" ========= FIRST TRANSACTION ========== ");
//		transactionPlayground.firstTransaction();
//		log.debug(" ========= SECOND TRANSACTION ========== ");
//		transactionPlayground.secondTransaction();
//		log.debug(" ========= END ========== ");

//		log.debug("=== Merge:Persist init ===");
//		mergePlayground.persistInitialData();
//		log.debug("=== Merge:READ ===");
//		mergePlayground.readFromDb();
//		log.debug("=== Merge:WRITE1 ===");
//		mergePlayground.client1();
//		log.debug("=== Merge:WRITE2 ===");
//		mergePlayground.client2();

//		log.debug("Uploading file...");
//		lobPlayground.uploadLargeClob();
//		log.debug("Downloading file...");
//		lobPlayground.downloadLargeClob();


//		dataPlayground.method();
		proasta.method();
	}

	@Autowired
	private StreamingDataDinDBEOIdeeProasta proasta;

	@Autowired
	private SpringDataPlayground dataPlayground;

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
