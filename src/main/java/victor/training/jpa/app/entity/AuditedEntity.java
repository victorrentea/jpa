package victor.training.jpa.app.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

//@MappedSuperclass // nu e entitate per se ci doar o serie de campuri comune

@Embeddable
public abstract class AuditedEntity {
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;
}
