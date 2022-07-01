package victor.training.jpa.app.entity;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfWeek;
	// embeddable names are contextualized using spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
	
	private Integer startHour;
	
	private Integer hours;
	
	private String roomId;

	protected TimeSlot() {}

	public TimeSlot(DayOfWeek dayOfWeek, int startHour, int hours, String roomId) {
		this.dayOfWeek = dayOfWeek;
		this.startHour = startHour;
		this.hours = hours;
		this.roomId = roomId;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
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
		return new TimeSlot(dayOfWeek,startHour, hours, roomId);
	}

	public TimeSlot withRoomId(String newRoom) {
		return new TimeSlot(dayOfWeek, startHour, hours, newRoom);
	}

	@Override
	public String toString() {
		return "TimeSlot{" +
			   "day=" + dayOfWeek +
			   ", startHour=" + startHour +
			   ", durationInHours=" + hours +
			   ", roomId='" + roomId + '\'' +
			   '}';
	}
}
