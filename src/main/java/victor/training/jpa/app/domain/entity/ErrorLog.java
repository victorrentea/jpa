package victor.training.jpa.app.domain.entity;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@ToString
public class ErrorLog {
   @Id
   @GeneratedValue
   private Long id;

   @Column(nullable = false)
   private String message;


   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }


}
