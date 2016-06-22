package br.udesc.ceavi.core.model.dao;

import java.sql.PreparedStatement;
import java.util.Iterator;

/**
 *
 * @author Samuel Felício Adriano
 * @param <Entity>
 */
public class EntityIterable<Entity extends br.udesc.ceavi.core.model.Entity> implements Iterable<Entity> {

    protected DAOGeneric<Entity> dao;
    protected PreparedStatement  statement;

    public EntityIterable(DAOGeneric<Entity> dao, PreparedStatement statement) {
        this.dao = dao;
        this.statement = statement;
    }

    @Override
    public Iterator<Entity> iterator() {
        return new EntityIterator(dao, statement);
    }

}