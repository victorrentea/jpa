package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ErrorLog.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ErrorLog_ {

	
	/**
	 * @see victor.training.jpa.app.entity.ErrorLog#comments
	 **/
	public static volatile ListAttribute<ErrorLog, ErrorComment> comments;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorLog#id
	 **/
	public static volatile SingularAttribute<ErrorLog, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorLog#message
	 **/
	public static volatile SingularAttribute<ErrorLog, String> message;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorLog
	 **/
	public static volatile EntityType<ErrorLog> class_;
	
	/**
	 * @see victor.training.jpa.app.entity.ErrorLog#tags
	 **/
	public static volatile SetAttribute<ErrorLog, ErrorTag> tags;

	public static final String COMMENTS = "comments";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String TAGS = "tags";

}

