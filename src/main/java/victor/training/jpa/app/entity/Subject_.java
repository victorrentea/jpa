package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Subject.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Subject_ {

	
	/**
	 * @see victor.training.jpa.app.entity.Subject#lastModifiedDate
	 **/
	public static volatile SingularAttribute<Subject, LocalDateTime> lastModifiedDate;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject#activities
	 **/
	public static volatile ListAttribute<Subject, TeachingActivity> activities;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject#lastModifiedBy
	 **/
	public static volatile SingularAttribute<Subject, String> lastModifiedBy;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject#name
	 **/
	public static volatile SingularAttribute<Subject, String> name;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject#active
	 **/
	public static volatile SingularAttribute<Subject, Boolean> active;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject#id
	 **/
	public static volatile SingularAttribute<Subject, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject#holderTeacher
	 **/
	public static volatile SingularAttribute<Subject, Teacher> holderTeacher;
	
	/**
	 * @see victor.training.jpa.app.entity.Subject
	 **/
	public static volatile EntityType<Subject> class_;

	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String ACTIVITIES = "activities";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String ID = "id";
	public static final String HOLDER_TEACHER = "holderTeacher";

}

