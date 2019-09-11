package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TimeSlot {

	@Enumerated(EnumType.STRING)
	private DayOfWeek day;
	
	private Integer startHour;
	
	private Integer durationInHours;
	
	private String roomId;

	public TimeSlot(DayOfWeek day, int startHour, int durationInHours, String roomId) {
		if (day == null) throw  new IllegalArgumentException("nu asa;");
		this.day = day;
		this.startHour = startHour;
		this.durationInHours = durationInHours;
		this.roomId = roomId;
	}

	public TimeSlot() { // pentru dragul de Hibernate
	}

	public DayOfWeek getDay() {
		return day;
	}


	public Integer getStartHour() {
		return startHour;
	}


	public Integer getDurationInHours() {
		return durationInHours;
	}


	public String getRoomId() {
		return roomId;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public void setDurationInHours(Integer durationInHours) {
		this.durationInHours = durationInHours;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
