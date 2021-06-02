package victor.training.jpa.magic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.repo.MagicRepo;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;

@SpringBootTest
public class MagicAppTest {
   @Autowired
   private MagicService service;
   @Autowired
   private MagicRepo repo;

   @WithMockUser(username = "superuser")
   @Test
   public void test() {
      Long id = service.one();

      Magic magic = repo.findOneById(id);
      assertThat(magic.getCreatedBy()).isEqualTo("superuser");
      assertThat(magic.getCreatedTime()).isCloseTo(now(), byLessThan(1, SECONDS));
   }
}
