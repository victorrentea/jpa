package victor.training.jpa.app.entity;

public interface Auditable {
  AuditedEntity getAuditedEntity();

  void setAuditedEntity(AuditedEntity auditedEntity);
}
