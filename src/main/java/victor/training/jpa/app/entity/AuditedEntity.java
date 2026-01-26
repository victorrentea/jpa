package victor.training.jpa.app.entity;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass // nu e entitate per se ci doar o serie de campuri comune
public abstract class AuditedEntity {
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;
}
