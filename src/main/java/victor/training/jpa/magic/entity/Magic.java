package victor.training.jpa.magic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
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
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@NoArgsConstructor(access = PRIVATE)
//@EntityListeners(AuditingEntityListener.class)// or global via orm.xml
public class Magic {
   @Id
   @GeneratedValue
   private Long id;
   private String name;

   private String pledge;
   private String turn;
   private String prestige;
   @CreatedBy
   private String createdBy;
   @CreatedDate
   private LocalDateTime createdTime;
   @LastModifiedBy
   private String lastModifiedBy;
   @LastModifiedDate
   private LocalDateTime lastModifiedTime;

   String stringuriCsv;

//   @Transient
//   List<String> stringuri;


//   public List<String> getStringuri() {
//      return Arrays.stream(stringuriCsv.split(",")).collect(Collectors.toList());
//   }
//
//   @PostLoad
//public void split() {
//   csv --> stirngurl
//}
//   @PrePersist
//   @PreUpdate
//   public void method() {
//      stringuriCsv = String.join(",", stringuri);
//   }

   public Magic(String name) {
      this.name = name;
   }

//   @Transient
//   private final List<DomainEvent> domainEvents = new ArrayList<>();

   public void perform() {
      EventPublisherHolder.getEventPublisher().publish(new MagicHappenedEvent(name));
//      domainEvents.add(new MagicHappenedEvent(name));
   }

//   @DomainEvents
//   public Collection<DomainEvent> events() {
//      return domainEvents;
//   }

}
