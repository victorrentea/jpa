package victor.training.jpa.app.domain.entity;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.DayOfWeek;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode
@Embeddable // ~ Value Objects
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	private DayOfWeek day;
	// embeddable names are contextualized using spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
	
	private int startHour;
	
	private int hours;
	
	private String roomId;

	private TimeSlot() {} // doar pt ochii lui Hibernate

	public TimeSlot(DayOfWeek day, int startHour, int hours, String roomId) {
		this.day = Objects.requireNonNull(day);
		this.startHour = startHour;
		this.hours = hours;
		this.roomId = Objects.requireNonNull(roomId);
	}

	public int getEndHour() {
		return startHour + hours;
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
