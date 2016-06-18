package br.udesc.ceavi.core.model.dao;

import br.udesc.ceavi.core.util.BeanUtils;
import br.udesc.ceavi.core.util.connection.ConnectionPostgreSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author Samuel Felício Adriano
 * @param <Entity>
 */
public class EntityIterator<Entity extends br.udesc.ceavi.core.model.Entity> implements RowIterator<Entity> {

    protected DAOGeneric<Entity> dao;
    protected PreparedStatement  statement;
    protected ResultSet          result;
    protected boolean            counted;
    protected int                cursor;
    protected int                count;


    public EntityIterator(DAOGeneric<Entity> dao, PreparedStatement statement) {
        this.dao       = dao;
        this.statement = statement;
        rewind();
    }

    @Override
    public void rewind() {
        try {
            result = statement.executeQuery();
            this.cursor  = 0;
            this.counted = false;
            this.count   = 0;
        } catch(SQLException exception) {
            ConnectionPostgreSQL.getInstance().close();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public boolean hasNext() {
        return this.cursor < count();
    }

    @Override
    public Entity next() {
        Entity entity = dao.getNewEntity();
        try {
            result.next();
            this.cursor++;
            for(Relation relation : dao.getRelationships().getAllRelations()) {
                BeanUtils.callSetter(entity, relation.getModelName(), dao.getValueFromStatement(result, relation));
            }

            return entity;
        } catch (SQLException exception) {
            ConnectionPostgreSQL.getInstance().close();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public int count() {
        if(counted) {
            return count;
        }
        try {
            String alias = "count_" + dao.getTableName();
            StringBuilder query = new StringBuilder("SELECT COUNT(1) AS ");
            query.append(alias).
                  append("\n  FROM (").
                  append(statement.toString()).
                  append(") ").
                  append(dao.getTableName());

            ResultSet counResult = ConnectionPostgreSQL.getInstance().getConnection().createStatement().executeQuery(query.toString());

            counResult.next();
            count   = counResult.getInt(1);
            counted = true;
            return count;
        } catch(SQLException exception) {
            ConnectionPostgreSQL.getInstance().close();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Iterator<Entity> iterator() {
        return this;
    }

}