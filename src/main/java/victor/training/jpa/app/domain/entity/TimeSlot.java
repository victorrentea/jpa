package victor.training.jpa.app.domain.entity;

import lombok.Data;

import java.time.DayOfWeek;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Embeddable
public class TimeSlot {
	@Enumerated(EnumType.STRING)
	private DayOfWeek day;
	
	private int startHour;
	
	private int durationInHours;
	
	private String roomId;

}
