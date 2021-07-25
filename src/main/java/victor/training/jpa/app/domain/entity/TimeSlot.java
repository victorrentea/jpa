package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Embeddable
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	private DayOfWeek day;
	// embeddable names are contextualized using spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
	
	private int startHour;
	
	private int hours;

	@ManyToOne
	private Room room;

	protected TimeSlot() {}

	public TimeSlot(DayOfWeek day, int startHour, int hours, Room room) {
		this.day = day;
		this.startHour = startHour;
		this.hours = hours;
		this.room = room;
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

	public Room getRoom() {
		return room;
	}


	public TimeSlot withDurationInHours(int hours) {
		return new TimeSlot(day,startHour, hours, room);
	}

	public TimeSlot withRoomId(Room newRoom) {
		return new TimeSlot(day, startHour, hours, newRoom);
	}

	@Override
	public String toString() {
		return "TimeSlot{" +
				 "day=" + day +
				 ", startHour=" + startHour +
				 ", durationInHours=" + hours +
				 ", roomId='" + room + '\'' +
				 '}';
	}
}
