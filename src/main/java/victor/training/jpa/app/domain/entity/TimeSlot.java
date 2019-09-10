package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	private final DayOfWeek day;
	
	private final int startHour;
	
	private final int durationInHours;
	
	private final String roomId;

	public TimeSlot(DayOfWeek day, int startHour, int durationInHours, String roomId) {
		if (day == null) throw  new IllegalArgumentException("nu asa;");
		this.day = day;
		this.startHour = startHour;
		this.durationInHours = durationInHours;
		this.roomId = roomId;
	}



	public DayOfWeek getDay() {
		return day;
	}


	public int getStartHour() {
		return startHour;
	}


	public int getDurationInHours() {
		return durationInHours;
	}


	public String getRoomId() {
		return roomId;
	}


	
}
