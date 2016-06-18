package br.udesc.ceavi.core.model.dao;

/**
 *
 * @author Samuel Felício Adriano
 */
public class Relation {

    private boolean key;
    private boolean sequential;
    private String  columnName;
    private String  modelName;
    private String  dbType;
    private Class   type;

    public Relation() {
    }

    public Relation(boolean key, boolean sequential, String columnName, String modelName, String dbType, Class type) {
        this.key = key;
        this.sequential = sequential;
        this.columnName = columnName;
        this.modelName = modelName;
        this.dbType = dbType;
        this.type = type;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public boolean isSequential() {
        return sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

}