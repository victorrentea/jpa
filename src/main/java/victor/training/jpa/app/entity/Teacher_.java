package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.DayOfWeek;
import victor.training.jpa.app.entity.Teacher.Grade;

@StaticMetamodel(Teacher.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Teacher_ {

	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#channels
	 **/
	public static volatile ListAttribute<Teacher, ContactChannel> channels;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#counselingStartHour
	 **/
	public static volatile SingularAttribute<Teacher, Integer> counselingStartHour;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#activities
	 **/
	public static volatile SetAttribute<Teacher, TeachingActivity> activities;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#grade
	 **/
	public static volatile SingularAttribute<Teacher, Grade> grade;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#name
	 **/
	public static volatile SingularAttribute<Teacher, String> name;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#counselingRoomId
	 **/
	public static volatile SingularAttribute<Teacher, String> counselingRoomId;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#counselingDay
	 **/
	public static volatile SingularAttribute<Teacher, DayOfWeek> counselingDay;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#details
	 **/
	public static volatile SingularAttribute<Teacher, TeacherDetails> details;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#counselingDurationInHours
	 **/
	public static volatile SingularAttribute<Teacher, Integer> counselingDurationInHours;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#id
	 **/
	public static volatile SingularAttribute<Teacher, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher#heldSubjects
	 **/
	public static volatile SetAttribute<Teacher, Subject> heldSubjects;
	
	/**
	 * @see victor.training.jpa.app.entity.Teacher
	 **/
	public static volatile EntityType<Teacher> class_;

	public static final String CHANNELS = "channels";
	public static final String COUNSELING_START_HOUR = "counselingStartHour";
	public static final String ACTIVITIES = "activities";
	public static final String GRADE = "grade";
	public static final String NAME = "name";
	public static final String COUNSELING_ROOM_ID = "counselingRoomId";
	public static final String COUNSELING_DAY = "counselingDay";
	public static final String DETAILS = "details";
	public static final String COUNSELING_DURATION_IN_HOURS = "counselingDurationInHours";
	public static final String ID = "id";
	public static final String HELD_SUBJECTS = "heldSubjects";

}

