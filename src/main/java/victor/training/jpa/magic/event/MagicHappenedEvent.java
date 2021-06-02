package victor.training.jpa.magic.event;

import lombok.Value;

@Value
public class MagicHappenedEvent implements DomainEvent {
   String magicName;
}
