package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@StaticMetamodel(TeachingActivity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class TeachingActivity_ {

	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#dayOfWeek
	 **/
	public static volatile SingularAttribute<TeachingActivity, DayOfWeek> dayOfWeek;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#lastModifiedDate
	 **/
	public static volatile SingularAttribute<TeachingActivity, LocalDateTime> lastModifiedDate;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#subject
	 **/
	public static volatile SingularAttribute<TeachingActivity, Subject> subject;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#startHour
	 **/
	public static volatile SingularAttribute<TeachingActivity, Integer> startHour;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#teachers
	 **/
	public static volatile SetAttribute<TeachingActivity, Teacher> teachers;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#lastModifiedBy
	 **/
	public static volatile SingularAttribute<TeachingActivity, String> lastModifiedBy;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#durationInHours
	 **/
	public static volatile SingularAttribute<TeachingActivity, Integer> durationInHours;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#id
	 **/
	public static volatile SingularAttribute<TeachingActivity, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity
	 **/
	public static volatile EntityType<TeachingActivity> class_;
	
	/**
	 * @see victor.training.jpa.app.entity.TeachingActivity#roomId
	 **/
	public static volatile SingularAttribute<TeachingActivity, String> roomId;

	public static final String DAY_OF_WEEK = "dayOfWeek";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String SUBJECT = "subject";
	public static final String START_HOUR = "startHour";
	public static final String TEACHERS = "teachers";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String DURATION_IN_HOURS = "durationInHours";
	public static final String ID = "id";
	public static final String ROOM_ID = "roomId";

}

