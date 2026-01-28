package victor.training.jpa.app.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StudentsGroup.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class StudentsGroup_ {

	
	/**
	 * @see victor.training.jpa.app.entity.StudentsGroup#emails
	 **/
	public static volatile ListAttribute<StudentsGroup, String> emails;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsGroup#code
	 **/
	public static volatile SingularAttribute<StudentsGroup, String> code;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsGroup#labs
	 **/
	public static volatile SetAttribute<StudentsGroup, LabActivity> labs;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsGroup#year
	 **/
	public static volatile SingularAttribute<StudentsGroup, StudentsYear> year;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsGroup#id
	 **/
	public static volatile SingularAttribute<StudentsGroup, Long> id;
	
	/**
	 * @see victor.training.jpa.app.entity.StudentsGroup
	 **/
	public static volatile EntityType<StudentsGroup> class_;

	public static final String EMAILS = "emails";
	public static final String CODE = "code";
	public static final String LABS = "labs";
	public static final String YEAR = "year";
	public static final String ID = "id";

}

