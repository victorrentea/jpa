package victor.training.jpa.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.jpa.app.common.data.EntityRepositoryFactoryBean;
import victor.training.jpa.app.domain.entity.ErrorLog;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableJpaAuditing
@Slf4j
@EnableSwagger2
//-javaagent:spring-instrument.jar -javaagent:aspectjweaver.jar
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class JpaApplication {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}

	@Autowired
	private DummyDataCreator dummyDataCreator;
	@Autowired
	private PersistMergePlayground playground; // spring injecteaza un proxy
//	private TransactionPlayground playground; // spring injecteaza un proxy
//	private TransactionControlPlayground playground; // spring injecteaza un proxy
	/// ( o subclasa generata dinamic care permite interceptarea tuturor metodelor public)

//	@Autowired
//	private MergePlayground mergePlayground;
//	@Autowired
//	private LobPlayground lobPlayground;


	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) throws IOException, SQLException {
		log.debug(">>>>>>>>>> Running Playground code... <<<<<<<<<<<<");
		log.debug(" ========= FIRST TRANSACTION ========== ");
		log.debug("Oare ce clasa mi-a injectat spring ? " + playground.getClass());
		playground.firstTransaction();//.setMessage("nu conteaza");
		log.debug(" ========= SECOND TRANSACTION ========== ");
		playground.secondTransaction();
		log.debug(" ========= END ========== ");

//		log.debug("=== Merge:Persist init ===");
//		mergePlayground.persistInitialData();
//		log.debug("=== Merge:READ ===");
//		mergePlayground.readFromDb();
//		log.debug("=== Merge:WRITE1 ===");
//		mergePlayground.client1();

//		log.debug("Uploading file...");
//		lobPlayground.uploadLargeClob();
//		log.debug("Downloading file...");
//		lobPlayground.downloadLargeClob();

	}
	

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
