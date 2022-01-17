package victor.training.jpa.perf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class JpaPerfApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(JpaPerfApplication.class)
            .profiles("insertDummyData")
            .run(args);
    }

}
