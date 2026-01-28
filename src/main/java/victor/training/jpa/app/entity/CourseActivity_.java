package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CourseActivity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class CourseActivity_ extends victor.training.jpa.app.entity.TeachingActivity_ {

	
	/**
	 * @see victor.training.jpa.app.entity.CourseActivity#year
	 **/
	public static volatile SingularAttribute<CourseActivity, StudentsYear> year;
	
	/**
	 * @see victor.training.jpa.app.entity.CourseActivity
	 **/
	public static volatile EntityType<CourseActivity> class_;

	public static final String YEAR = "year";

}

