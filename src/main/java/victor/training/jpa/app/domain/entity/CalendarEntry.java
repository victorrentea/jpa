package victor.training.jpa.app.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.With;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class CalendarEntry {
   // cum zice bizu, daca are un termen consacrat pentru asta.

   @Enumerated(EnumType.STRING)
   @NotNull
   private DayOfWeek day;

//   @Column(nullable = false)
   private Integer startHour;

   private Integer durationInHours;

   @With
   private String roomId;

//   static {
//      new CalendarEntry().with
//   }

   CalendarEntry() {} // love for hibernate

   public CalendarEntry(DayOfWeek day, int startHour, int durationInHours, String roomId) {
      this.day = requireNonNull(day);
      this.startHour = startHour;
      this.durationInHours = durationInHours;
      this.roomId = requireNonNull(roomId);
//      magicObtineUnValidatorDeLaSpring.validate(this);
   }
}
