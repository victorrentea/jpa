package victor.training.jpa.perf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@SpringBootApplication

public class JpaPerfApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaPerfApplication.class, args);
    }

}
