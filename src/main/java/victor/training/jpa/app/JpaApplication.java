package victor.training.jpa.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class JpaApplication {

	@EventListener(ContextRefreshedEvent.class)
	public void onApplicationEvent() throws IOException, SQLException {
		log.debug(">>>>>>>>>> App started ok! Congrats🎉 <<<<<<<<<<<<");
		log.debug(">>> Now have a look at the generated schema by connecting to the db <<<");
	}
	

	public static void main(String[] args) {
		new SpringApplicationBuilder(JpaApplication.class)
				.listeners(new TestDBConnectionInitializer())
				.run(args);
	}
}
