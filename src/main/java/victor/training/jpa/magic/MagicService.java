package victor.training.jpa.magic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.magic.entity.Magic;
import victor.training.jpa.magic.event.MagicHappenedEvent;
import victor.training.jpa.magic.repo.MagicRepo;

@Slf4j
@RequiredArgsConstructor
@Service
public class MagicService {
   private final MagicRepo repo;
   private final OtherService other;

   @Transactional
   public Long one() {
      Magic magic = new Magic("C");
      repo.save(magic);
      return magic.getId();
   }

   @Transactional
   public void two() {
      Magic magic = repo.findOneById(1L);
      magic.setName("New Name");
      // System.out.println(repo.myQuery(100L));
   }
}
// TODO List:
// @CreatedBy/Time Spring Data+Security
// javax.validation
// @DomainEvents + AbstractAggregateRoot + EventPublisherHolder
// QueryDSL + Specifications / Criteria Metamodel
// @NonNullApi on package-info
// Custom Repo Base
// JPA: Auto-flushing, Write Cache
// TX: propagation


@Slf4j
@RequiredArgsConstructor
@Service
class OtherService {
   private final MagicRepo repo;
   @Transactional
   public void someMethod() {

   }
   @EventListener
   public void afterMagic(MagicHappenedEvent magicHappened) {
      log.debug("Magic happened: " + magicHappened);
      repo.save(new Magic(magicHappened.getMagicName() + " Reversed"));
//      throw new RuntimeException("Bad Magic");
   }
}
