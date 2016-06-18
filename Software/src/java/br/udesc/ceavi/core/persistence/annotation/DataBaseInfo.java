package br.udesc.ceavi.core.persistence.annotation;

import br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DataBaseContainer.class)

/**
 * Class that indicates the relationships between the entity and the database
 *
 * @author Samuel Felício Adriano <felicio.samuel@gmail.com>
 * @since  08/05/2016
 */
public @interface DataBaseInfo {

    /**
     * Set the field as key
     *
     * @return boolean - if it's key or not
     */
    boolean key() default false;

    /**
     * Defines the name of the collumn in the DataBase
     *
     * @return String - the name of the column
     */
    String columnName();

    /**
     * Set the type of the column in the DataBase
     *
     * @return EntityDataBaseTypeRelation - the DataType relation of the class
     * of the field and the DataBase
     */
    EntityDataBaseTypeRelation dataType();


    /**
     * Set the field as key
     *
     * @return boolean - if it's key or not
     */
    boolean sequential() default false;

}