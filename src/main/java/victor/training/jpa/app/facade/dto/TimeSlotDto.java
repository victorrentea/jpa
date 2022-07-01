package victor.training.jpa.app.facade.dto;

import lombok.Data;
import victor.training.jpa.app.entity.TimeSlot;

import java.time.DayOfWeek;

@Data
public class TimeSlotDto {
	private DayOfWeek day;
	private int startHour;
	private int durationInHours;
	private String roomId;
	
	public TimeSlotDto() {
	}
	public TimeSlotDto(DayOfWeek day, int startHour, int durationInHours, String roomId) {
		this.day = day;
		this.startHour = startHour;
		this.durationInHours = durationInHours;
		this.roomId = roomId;
	}
	public TimeSlotDto(TimeSlot timeSlot) {
		this.day = timeSlot.getDayOfWeek();
		this.startHour = timeSlot.getStartHour();
		this.durationInHours = timeSlot.getHours();
		this.roomId = timeSlot.getRoomId();
	}

	public TimeSlot toTimeSlot() {
		return new TimeSlot(
				day,
				startHour,
				durationInHours,
				roomId
		);
	}
}
