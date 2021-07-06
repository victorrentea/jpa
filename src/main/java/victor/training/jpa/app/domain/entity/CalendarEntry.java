package victor.training.jpa.app.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Data
@Embeddable
public class CalendarEntry {
   // cum zice bizu, daca are un termen consacrat pentru asta.

   @Enumerated(EnumType.STRING)
   private DayOfWeek day;

//   @Column(nullable = false)
   private Integer startHour;

   private Integer durationInHours;

   private String roomId;


   CalendarEntry() {} // love for hibernate

   public CalendarEntry(DayOfWeek day, int startHour, int durationInHours, String roomId) {
      this.day = requireNonNull(day);
      this.startHour = startHour;
      this.durationInHours = durationInHours;
      this.roomId = requireNonNull(roomId);
   }
}
