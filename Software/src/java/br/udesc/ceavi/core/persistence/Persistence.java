package br.udesc.ceavi.core.persistence;

/**
 *
 * @author Samuel Felício Adriano
 */
public abstract class Persistence {

    public static final Persistence getPersistence(PersistenceType persistenceType) {
        switch(persistenceType) {
            case JDBC:
                return new br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory();
        }

        return null;
    }

}