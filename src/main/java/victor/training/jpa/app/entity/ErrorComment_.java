package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ErrorComment.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ErrorComment_ {

	
	/**
	 * @see victor.training.jpa.app.entity.ErrorComment#id
	 **/
	public static volatile SingularAttribute<ErrorComment, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorComment#text
	 **/
	public static volatile SingularAttribute<ErrorComment, String> text;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorComment
	 **/
	public static volatile EntityType<ErrorComment> class_;

	public static final String ID = "id";
	public static final String TEXT = "text";

}

