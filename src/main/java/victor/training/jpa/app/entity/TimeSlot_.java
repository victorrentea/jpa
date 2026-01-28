package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.DayOfWeek;

@StaticMetamodel(TimeSlot.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class TimeSlot_ {

	
	/**
	 * @see victor.training.jpa.app.entity.TimeSlot#getHours
	 **/
	public static volatile SingularAttribute<TimeSlot, Integer> hours;
	
	/**
	 * @see victor.training.jpa.app.entity.TimeSlot#getDayOfWeek
	 **/
	public static volatile SingularAttribute<TimeSlot, DayOfWeek> dayOfWeek;
	
	/**
	 * @see victor.training.jpa.app.entity.TimeSlot#getStartHour
	 **/
	public static volatile SingularAttribute<TimeSlot, Integer> startHour;
	
	/**
	 * @see victor.training.jpa.app.entity.TimeSlot
	 **/
	public static volatile EmbeddableType<TimeSlot> class_;
	
	/**
	 * @see victor.training.jpa.app.entity.TimeSlot#getRoomId
	 **/
	public static volatile SingularAttribute<TimeSlot, String> roomId;

	public static final String HOURS = "hours";
	public static final String DAY_OF_WEEK = "dayOfWeek";
	public static final String START_HOUR = "startHour";
	public static final String ROOM_ID = "roomId";

}

