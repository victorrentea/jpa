package victor.training.jpa.perf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import victor.training.jpa.app.JpaApplication;

@EnableCaching
@SpringBootApplication
public class JpaPerfApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaPerfApplication.class, args);
    }

}
