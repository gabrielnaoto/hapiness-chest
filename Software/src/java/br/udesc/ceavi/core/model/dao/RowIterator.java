package br.udesc.ceavi.core.model.dao;

import br.udesc.ceavi.core.util.Countable;
import java.util.Iterator;

/**
 *
 * @author Samuel Felício Adriano
 * @param <Entity>
 */
public interface RowIterator<Entity extends br.udesc.ceavi.core.model.Entity> extends Iterator<Entity>, Countable, Iterable<Entity> {

    public void rewind();

}
