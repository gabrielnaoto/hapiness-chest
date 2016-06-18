package br.udesc.ceavi.core.persistence;

import java.util.Date;

/**
 * Provides the relations between the fields classes of the entity and the
 * Data Types of the DataBase
 *
 * @author Samuel Felício Adriano <felicio.samuel@gmail.com>
 * @since  08/05/2016
 */
public enum EntityDataBaseTypeRelation {

     DOUBLE_NUMERIC(double.class, "numeric")
    ,INT_INTEGER   (int.class   , "int")
    ,DATE_DATE     (Date.class  , "date")
    ,LONG_INT      (long.class  , "int")
    ,LONG_BIGINT   (long.class  , "bigint")
    ,STRING_VARCHAR(String.class, "varchar");


    private Class  type;
    private String postgresql;


    private EntityDataBaseTypeRelation(Class type, String postgresql) {
        this.type       = type;
        this.postgresql = postgresql;
    }

    public Class getType() {
        return type;
    }

    public String getPostgresql() {
        return postgresql;
    }

}