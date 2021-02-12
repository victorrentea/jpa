package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	@Column(name = "DAY")
	private DayOfWeek day;
	
	@Column(name = "START_HOUR")
	private int startHour;
	
	@Column(name = "DURATION_HOURS")
	private int durationInHours;
	
	private String roomId;

	protected TimeSlot() {}

	public TimeSlot(DayOfWeek day, int startHour, int durationInHours, String roomId) {
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


	public TimeSlot withDurationInHours(int hours) {
		return new TimeSlot(day,startHour, durationInHours, roomId);
	}

	public TimeSlot withRoomId(String newRoom) {
		return new TimeSlot(day, startHour, durationInHours, newRoom);
	}

	@Override
	public String toString() {
		return "TimeSlot{" +
				 "day=" + day +
				 ", startHour=" + startHour +
				 ", durationInHours=" + durationInHours +
				 ", roomId='" + roomId + '\'' +
				 '}';
	}
}
