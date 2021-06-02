package victor.training.jpa.magic.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import victor.training.jpa.magic.event.DomainEvent;

public interface EventPublisher {
   void publish(DomainEvent domainEvent);
}
