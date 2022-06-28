package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@ToString
public class ErrorLog {
   private Long id;

   private String message;


   private Set<ErrorTag> tags = new HashSet<>();

   public ErrorLog() {
   }

   public ErrorLog(String message) {
      this.message = message;
   }



}
