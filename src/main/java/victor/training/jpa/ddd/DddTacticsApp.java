package victor.training.jpa.ddd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DddTacticsApp implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(DddTacticsApp.class, args);
   }

   @Override
   public void run(String... args) throws Exception {

   }
}
