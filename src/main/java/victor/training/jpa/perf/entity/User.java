package victor.training.jpa.perf.entity;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ToString
public class User {
   @Id
   @GeneratedValue
   private Long id;
   private String username;

   public User() {
   }

   public User(String username) {
      this.username = username;
   }

   public Long getId() {
      return id;
   }

   public String getUsername() {
      return username;
   }
}
