package victor.training.jpa.app.entity;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	private DayOfWeek day;
	// embeddable names are contextualized using spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
	
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

	public DayOfWeek getDay() {
		return day;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getHours() {
		return hours;
	}

	public String getRoomId() {
		return roomId;
	}


	public TimeSlot withDurationInHours(int hours) {
		return new TimeSlot(day,startHour, hours, roomId);
	}

	public TimeSlot withRoomId(String newRoom) {
		return new TimeSlot(day, startHour, hours, newRoom);
	}

	@Override
	public String toString() {
		return "TimeSlot{" +
				 "day=" + day +
				 ", startHour=" + startHour +
				 ", durationInHours=" + hours +
				 ", roomId='" + roomId + '\'' +
				 '}';
	}
}
