package victor.training.jpa.magic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import victor.training.jpa.magic.event.DomainEvent;
import victor.training.jpa.magic.event.MagicHappenedEvent;
import victor.training.jpa.magic.event.publisher.EventPublisherHolder;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@NoArgsConstructor(access = PRIVATE)
//@EntityListeners(AuditingEntityListener.class) // or global via orm.xml
public class Magic {
   @Id
   @GeneratedValue
   private Long id;
   private String name;


   @CreatedBy
   private String createdBy;
   @CreatedDate
   private LocalDateTime createdTime;
   @LastModifiedBy
   private String lastModifiedBy;
   @LastModifiedDate
   private LocalDateTime lastModifiedTime;

   public Magic(String name) {
      this.name = name;
   }

//   @Transient
//   private final List<DomainEvent> domainEvents = new ArrayList<>();

   public void perform() {
      /// LOGIC
      // do some changes to this Aggregate that should trigger further things
//      domainEvents.add(new MagicHappenedEvent(name));
      EventPublisherHolder.getEventPublisher().publish(new MagicHappenedEvent(name));
   }

//   @DomainEvents
//   public List<DomainEvent> getDomainEvents() {
//      return domainEvents;
//   }
}
