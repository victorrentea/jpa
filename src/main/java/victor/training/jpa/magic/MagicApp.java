package victor.training.jpa.magic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.event.MagicHappenedEvent;
import victor.training.jpa.magic.repo.MagicRepo;
import victor.training.jpa.magic.repo.base.CustomJpaRepositoryFactoryBean;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
public class MagicApp implements CommandLineRunner {
   private final MagicService service;
   private final MagicRepo repo;

   public static void main(String[] args) {
      SpringApplication.run(MagicApp.class, args);
   }

//   @Bean
//   public AuditorAware<String> auditor() {
//      return () -> Optional.of("jdoe");
//   }


   @Override
   public void run(String... args) throws Exception {
      log.debug("===ONE===");
      service.one();
      log.debug("===TWO===");
      service.two();
      log.debug("=========");
   }

}
