package victor.training.jpa.app.web;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Sheep.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Sheep_ {

	
	/**
	 * @see victor.training.jpa.app.web.Sheep#name
	 **/
	public static volatile SingularAttribute<Sheep, String> name;
	
	/**
	 * @see victor.training.jpa.app.web.Sheep#id
	 **/
	public static volatile SingularAttribute<Sheep, Long> id;
	
	/**
	 * @see victor.training.jpa.app.web.Sheep#sn
	 **/
	public static volatile SingularAttribute<Sheep, String> sn;
	
	/**
	 * @see victor.training.jpa.app.web.Sheep
	 **/
	public static volatile EntityType<Sheep> class_;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String SN = "sn";

}

