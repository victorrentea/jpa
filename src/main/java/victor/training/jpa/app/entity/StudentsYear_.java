package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StudentsYear.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class StudentsYear_ {

	
	/**
	 * @see victor.training.jpa.app.entity.StudentsYear#courses
	 **/
	public static volatile SetAttribute<StudentsYear, CourseActivity> courses;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsYear#code
	 **/
	public static volatile SingularAttribute<StudentsYear, String> code;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsYear#groups
	 **/
	public static volatile ListAttribute<StudentsYear, StudentsGroup> groups;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsYear#id
	 **/
	public static volatile SingularAttribute<StudentsYear, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsYear
	 **/
	public static volatile EntityType<StudentsYear> class_;

	public static final String COURSES = "courses";
	public static final String CODE = "code";
	public static final String GROUPS = "groups";
	public static final String ID = "id";

}

