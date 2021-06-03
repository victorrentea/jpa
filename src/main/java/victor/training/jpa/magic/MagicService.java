package victor.training.jpa.magic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.entity.QMagic;
import victor.training.jpa.magic.event.MagicHappenedEvent;
import victor.training.jpa.magic.repo.MagicRepo;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MagicService {
   private final MagicRepo repo;
   private final OtherService other;

   @Transactional
   public Long one() {
      Magic magic = new Magic("C");
      magic.perform();
      return repo.save(magic).getId();
   }

//   @Transactional
   public void two() {
      Magic magic = repo.findById(1L).get();
      magic.setName("New Name");
      magic.perform();
      repo.save(magic);
   }

}
// TODO List:
// @CreatedBy/Time Spring Data+Security
// javax.validation
// @DomainEvents + AbstractAggregateRoot + EventPublisherHolder
// Custom Repo Base
// QueryDSL + Specifications / Criteria Metamodel
// @NonNullApi on package-info
// JPA: Auto-flushing, Write Cache
// TX: propagation [brief] -> https://victorrentea.teachable.com/p/transactions-in-spring


@Slf4j
@RequiredArgsConstructor
@Service
class OtherService {
   private final MagicRepo repo;
   @Transactional
   public void someMethod() {

   }
   @TransactionalEventListener
   @Transactional
   public void afterMagic(MagicHappenedEvent magicHappened) {
      log.debug("Magic happened: " + magicHappened);
      repo.save(new Magic(magicHappened.getMagicName() + " Reversed"));
   }
//   @EventListener
//   public void afterMagic2(MagicHappenedEvent magicHappened) {
//      log.debug("Magic happenedWOW!: " + magicHappened);
//   }
}
