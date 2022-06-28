package victor.training.jpa.magic.event.publisher;


public class EventPublisherHolder {
   private static EventPublisher eventPublisher = e -> {
      throw new RuntimeException("No Event Publisher Registered");
   };

   public static void setEventPublisher(EventPublisher eventPublisher) {
      EventPublisherHolder.eventPublisher = eventPublisher;
   }

   public static EventPublisher getEventPublisher() {
      return eventPublisher;
   }
}
