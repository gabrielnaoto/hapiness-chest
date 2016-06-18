package br.udesc.ceavi.core.model.dao;

import br.udesc.ceavi.core.persistence.Persistible;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;
import br.udesc.ceavi.core.util.BeanUtils;
import br.udesc.ceavi.core.util.StringUtils;
import br.udesc.ceavi.core.util.connection.Connection;
import br.udesc.ceavi.core.util.connection.ConnectionPostgreSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Felício Adriano
 * @param <DAOEntity>
 */
public abstract class DAOGeneric<DAOEntity extends br.udesc.ceavi.core.model.Entity> implements Persistible<DAOEntity> {


    public static final int EXECUTE_UPDATE_SUCCEEDED = 1,
                            EXECUTE_UPDATE_FAILED    = 2;

    public static final String OPERATOR_EQUAL = "=";


    protected Connection               connection;
    protected DAOEntity                entity;
    protected Relationships<DAOEntity> relationships;


    public DAOGeneric() {
        this.entity   = getNewEntity();
        connection    = ConnectionPostgreSQL.getInstance();
        relationships = new Relationships<>();
        setRelations();
    }


    protected void setRelations() {
        for(java.lang.reflect.Field field : entity.getClass().getDeclaredFields()) {
            for(DataBaseInfo dbInfo : field.getDeclaredAnnotationsByType(DataBaseInfo.class)) {
                this.relationships.addRelation(dbInfo.key(),
                                               dbInfo.sequential(),
                                               dbInfo.columnName(),
                                               StringUtils.toBeanFormat(dbInfo.columnName()),
                                               dbInfo.dataType().getPostgresql(),
                                               dbInfo.dataType().getType());
            }
        }
    }

    public Relationships<DAOEntity> getRelationships() {
        return relationships;
    }

    public String getTableName() {
        Table table = entity.getClass().getAnnotation(Table.class);

        return table.name();
    }

    public String getTableNameComplete() {
        Table table = entity.getClass().getAnnotation(Table.class);
        if(table.schema().isEmpty()) {
            return table.name();
        }

        return table.schema().toUpperCase() + "." + table.name().toLowerCase();
    }

    @Override
    public boolean insert(DAOEntity entity) {
        try {
            boolean sucess = runInsert(entity);
            connection.endTransaction(sucess);
            return sucess;
        } catch(SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

    protected boolean execute(String query) {
        try {
            connection.getConnection().prepareStatement(query).execute();
            connection.endTransaction(true);
            return true;
        } catch (SQLException ex) {
            catchSQLException(ex);
            return false;
        }
    }

    protected ResultSet executeQuery(String query) {
        try {
            return connection.getConnection().prepareStatement(query.toString()).executeQuery();
        } catch (SQLException ex) {
            catchSQLException(ex);
            return null;
        }
    }

    protected boolean runInsert(DAOEntity entity) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(this.getTableNameComplete()).
                      append(" (").
                      append(String.join(", ", this.relationships.getAllNonSequentialsColumnsNames())).
                      append(") VALUES (").
                      append(String.join(", ", relationships.getAllNonSequentialsPreparedParameters())).
                      append(") RETURNING ").
                      append(String.join(", ", relationships.getAllColumnsNames()));

        PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

        for(int i = 0; i < relationships.getAllNonSequentialsTypes().size(); i++) {
            setValueFromBeanIntoStatement(entity, statement, relationships.getAllNonSequentialsTypes().get(i), relationships.getAllNonSequentialsColumnsNames().get(i), i);
        }

        ResultSet result = statement.executeQuery();
        if(result.next()) {
            for(Relation relation : getRelationships().getAllRelations()) {
                BeanUtils.callSetter(entity, relation.getModelName(), getValueFromStatement(result, relation));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(DAOEntity entity) {
        try {
            boolean sucess = runDelete(entity);
            connection.endTransaction(sucess);
            return sucess;
        } catch(SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

    protected boolean runDelete(DAOEntity entity) throws SQLException {
        StringBuilder query = new StringBuilder("DELETE FROM ").
                      append(this.getTableNameComplete()).
                      append(" WHERE TRUE ");

        for(int i = 0; i < relationships.getAllKeyColumnsNames().size(); i++) {
            query.append("AND ").
                  append(relationships.getAllKeyColumnsNames().get(i)).
                  append(" " + OPERATOR_EQUAL + " ?");
        }

        PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

        for(int i = 0; i < relationships.getAllKeyTypes().size(); i++) {
            setValueFromBeanIntoStatement(entity, statement, relationships.getAllKeyTypes().get(i), relationships.getAllKeyColumnsNames().get(i), i);
        }

        return statement.executeUpdate() == EXECUTE_UPDATE_SUCCEEDED;
    }

    @Override
    public boolean update(DAOEntity entity) {
        try {
            boolean sucess = runUpdate(entity);
            connection.endTransaction(sucess);
            return sucess;
        } catch(SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

    protected boolean runUpdate(DAOEntity entity) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE ").
                      append(this.getTableNameComplete()).
                      append(" SET (").
                      append(String.join(", ", relationships.getAllColumnsNames())).
                      append(") " + OPERATOR_EQUAL + "(").
                      append(String.join(", ", relationships.getAllPreparedParameters())).
                      append(")").
                      append("\nWHERE TRUE ");

        for(int i = 0; i < relationships.getAllKeyColumnsNames().size(); i++) {
            query.append("AND ").
                  append(relationships.getAllKeyColumnsNames().get(i)).
                  append(" " + OPERATOR_EQUAL + " ?");
        }

        PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

        int index = 0;
        for(int i = 0; i < relationships.getAllTypes().size(); i++) {
            setValueFromBeanIntoStatement(entity, statement, relationships.getAllTypes().get(i), relationships.getAllColumnsNames().get(i), index);
            index++;
        }
        for(int i = 0; i < relationships.getAllKeyTypes().size(); i++) {
            setValueFromBeanIntoStatement(entity, statement, relationships.getAllKeyTypes().get(i), relationships.getAllKeyColumnsNames().get(i), index);
            index++;
        }

        return statement.executeUpdate() == EXECUTE_UPDATE_SUCCEEDED;
    }

    public PreparedStatement getSqlGetAll() {
        StringBuilder query = new StringBuilder("SELECT ");

        java.util.List<String> projection = new java.util.ArrayList();
        relationships.getAllRelations().stream().forEach((relation) -> {
            projection.add(relation.getColumnName());
        });

        query.append(String.join(",\n", projection))
             .append("\nFROM ")
             .append(this.getTableNameComplete());

        PreparedStatement statement;
        try {
            statement = connection.getConnection().prepareStatement(query.toString());
        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return statement;
    }

    @Override
    public Iterable<DAOEntity> getAll() {
        return new EntityIterable<DAOEntity>(this, getSqlGetAll());
    }

    @Override
    public boolean persists(DAOEntity entity) {
        if(!exists(entity)) {
            return false;
        }
        StringBuilder query = new StringBuilder("SELECT *");
        query.append("\nFROM ")
             .append(this.getTableNameComplete())
             .append("\nWHERE TRUE");

        for(int i = 0; i < relationships.getAllKeyColumnsNames().size(); i++) {
            query.append("\nAND ").
                  append(relationships.getAllKeyColumnsNames().get(i)).
                  append(" " + OPERATOR_EQUAL + " ?");
        }

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

            for(int i = 0; i < relationships.getAllKeyTypes().size(); i++) {
                setValueFromBeanIntoStatement(entity, statement, relationships.getAllKeyTypes().get(i), relationships.getAllKeyColumnsNames().get(i), i);
            }

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                for(Relation relation : getRelationships().getAllRelations()) {
                    BeanUtils.callSetter(entity, relation.getModelName(), getValueFromStatement(result, relation));
                }
                return true;
            }
            return false;
        } catch(SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

    @Override
    public boolean exists(DAOEntity entity) {
        try {
            StringBuilder query = new StringBuilder("SELECT EXISTS(SELECT 1");
            query.append("\nFROM ")
                 .append(this.getTableNameComplete())
                 .append("\nWHERE TRUE");

            for(int i = 0; i < relationships.getAllKeyColumnsNames().size(); i++) {
                query.append("\nAND ").
                      append(relationships.getAllKeyColumnsNames().get(i)).
                      append(" " + OPERATOR_EQUAL + " ?");
            }
            query.append(")");

            PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

            for(int i = 0; i < relationships.getAllKeyTypes().size(); i++) {
                setValueFromBeanIntoStatement(entity, statement, relationships.getAllKeyTypes().get(i), relationships.getAllKeyColumnsNames().get(i), i);
            }

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return result.getString(1).equals("t");
            }
            return false;
        } catch(SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

    @Override
    public DAOEntity findOne(DAOEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<DAOEntity> findAll(DAOEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void setValueFromBeanIntoStatement(DAOEntity entity, PreparedStatement statement, Class<?> type, String propertyName, int index) throws SQLException {
        propertyName = StringUtils.toBeanFormat(propertyName);
        switch(type.getName()) {
            case "java.lang.Double":
            case "double":
                statement.setDouble(index + 1, BeanUtils.callGetter(entity, propertyName, true));
            break;

            case "java.lang.Float":
            case "float":
                statement.setDouble(index + 1, BeanUtils.callGetter(entity, propertyName, true));
            break;

            case "java.lang.Long":
            case "long":
                statement.setLong(index + 1, BeanUtils.callGetter(entity, propertyName, true));
            break;

            case "java.lang.Integer":
            case "int":
                statement.setInt(index + 1, BeanUtils.callGetter(entity, propertyName, true));
            break;

            case "java.lang.String":
                statement.setString(index + 1, BeanUtils.callGetter(entity, propertyName, true));
            break;

            case "java.util.Date":
                statement.setDate(index + 1, BeanUtils.callGetter(entity, propertyName, true));

            default:
                throw new RuntimeException("Type not recognized!");
        }
    }

    public Object getValueFromStatement(ResultSet result, Relation relation) throws SQLException {
        String columnName = relation.getColumnName();
        switch(relation.getType().getName()) {
            case "java.lang.Double":
            case "double":
                return result.getDouble(columnName);

            case "java.lang.Float":
            case "float":
                return result.getFloat(columnName);

            case "java.lang.Long":
            case "long":
                return result.getLong(columnName);

            case "java.lang.Integer":
            case "int":
                return result.getInt(columnName);

            case "java.lang.String":
                return result.getString(columnName);

            default:
                throw new RuntimeException("Type not recognized!");
        }
    }

    protected void catchSQLException(SQLException exception) {
        String message;
        try {
            message = exception.getMessage();
            this.connection.getConnection().rollback();
        } catch(SQLException rollbackException) {
            message  = "Error on rollback!:" + rollbackException;
            message += "\n" + exception.getMessage();
        }
        this.connection.close();
        throw new RuntimeException(message);
    }

}