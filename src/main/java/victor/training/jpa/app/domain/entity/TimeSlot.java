package victor.training.jpa.app.domain.entity;

import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@ToString
public class TimeSlot {

	private DayOfWeek day;

	private int startHour;
	
	private int hours;
	
	private String roomId;

	protected TimeSlot() {}

	public TimeSlot(DayOfWeek day, int startHour, int hours, String roomId) {
		this.day = day;
		this.startHour = startHour;
		this.hours = hours;
		this.roomId = roomId;
	}

}
