package victor.training.jpa.app.domain.entity;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;

@Getter // I hope no one sees this :P
@Embeddable
public class CounselingTime {
   @Enumerated(EnumType.STRING)
   private DayOfWeek counselingDay;

   private Integer counselingStartHour;

   private Integer counselingDurationInHours;

   private String counselingRoomId;

   // for the eyes of Hibernate only
   protected CounselingTime() {
   }

   public CounselingTime(DayOfWeek counselingDay, Integer counselingStartHour, Integer counselingDurationInHours, String counselingRoomId) {
      this.counselingDay = counselingDay;
      this.counselingStartHour = counselingStartHour;
      this.counselingDurationInHours = counselingDurationInHours;
      this.counselingRoomId = counselingRoomId;
   }
}
