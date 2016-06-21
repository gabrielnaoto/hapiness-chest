package br.udesc.ceavi.core.util.connection;

/**
 * Classe de conexão para o sgbd PostgreSQL.
 *
 * @author Samuel Felício Adriano
 */
public class ConnectionPostgreSQL extends Connection {

    private static String database = "bau";

    protected static ConnectionPostgreSQL instance = null;


    private ConnectionPostgreSQL(User user, String host, String port, String dataBase) {
        super(user, host, port, dataBase);

        this.sgbd = SGBD.POSTGRE_SQL;
    }

    public static Connection getInstance() {
        if(instance == null) {
            User usuario = new User("postgres", "root");
            instance     = new ConnectionPostgreSQL(usuario, "localhost", "5432", database);
        }

        return instance;
    }

}