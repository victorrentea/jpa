package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ErrorTag.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ErrorTag_ {

	
	/**
	 * @see victor.training.jpa.app.entity.ErrorTag#id
	 **/
	public static volatile SingularAttribute<ErrorTag, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorTag#label
	 **/
	public static volatile SingularAttribute<ErrorTag, String> label;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorTag
	 **/
	public static volatile EntityType<ErrorTag> class_;

	public static final String ID = "id";
	public static final String LABEL = "label";

}

