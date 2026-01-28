package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LabActivity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class LabActivity_ extends victor.training.jpa.app.entity.TeachingActivity_ {

	
	/**
	 * @see victor.training.jpa.app.entity.LabActivity
	 **/
	public static volatile EntityType<LabActivity> class_;
	
	/**
	 * @see victor.training.jpa.app.entity.LabActivity#group
	 **/
	public static volatile SingularAttribute<LabActivity, StudentsGroup> group;

	public static final String GROUP = "group";

}

