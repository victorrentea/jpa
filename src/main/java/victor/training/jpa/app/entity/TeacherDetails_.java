package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TeacherDetails.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class TeacherDetails_ {

	
	/**
	 * @see victor.training.jpa.app.entity.TeacherDetails#cv
	 **/
	public static volatile SingularAttribute<TeacherDetails, String> cv;
	
	/**
	 * @see victor.training.jpa.app.entity.TeacherDetails#id
	 **/
	public static volatile SingularAttribute<TeacherDetails, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.TeacherDetails
	 **/
	public static volatile EntityType<TeacherDetails> class_;

	public static final String CV = "cv";
	public static final String ID = "id";

}

