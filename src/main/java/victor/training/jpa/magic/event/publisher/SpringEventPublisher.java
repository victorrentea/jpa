package victor.training.jpa.magic.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import victor.training.jpa.magic.event.DomainEvent;

import javax.annotation.PostConstruct;

@Component
public class SpringEventPublisher implements EventPublisher {
   private final ApplicationEventPublisher applicationEventPublisher;

   public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
      this.applicationEventPublisher = applicationEventPublisher;
   }
   @PostConstruct
   public void selfInject() {
      EventPublisherHolder.setEventPublisher(this);
   }

   @Override
   public void publish(DomainEvent domainEvent) {
      applicationEventPublisher.publishEvent(domainEvent);
   }
}
