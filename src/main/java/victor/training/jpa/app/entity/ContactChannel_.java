package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import victor.training.jpa.app.entity.ContactChannel.Type;

@StaticMetamodel(ContactChannel.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ContactChannel_ {

	
	/**
	 * @see victor.training.jpa.app.entity.ContactChannel#type
	 **/
	public static volatile SingularAttribute<ContactChannel, Type> type;
	
	/**
	 * @see victor.training.jpa.app.entity.ContactChannel
	 **/
	public static volatile EmbeddableType<ContactChannel> class_;
	
	/**
	 * @see victor.training.jpa.app.entity.ContactChannel#value
	 **/
	public static volatile SingularAttribute<ContactChannel, String> value;

	public static final String TYPE = "type";
	public static final String VALUE = "value";

}

