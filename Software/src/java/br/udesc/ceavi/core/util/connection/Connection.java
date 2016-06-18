package br.udesc.ceavi.core.util.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de Conexão com o Banco de Dados.
 *
 * @author Samuel Felício Adriano
 */
public abstract class Connection {

    protected SGBD    sgbd;
    protected User    user;
    protected String  host;
    protected String  port;
    protected String  dataBase;
    protected boolean connected;

    protected java.sql.Connection cnx;


    protected Connection(User user, String host, String port, String dataBase) {
        this.user       = user;
        this.host       = host;
        this.port       = port;
        this.dataBase   = dataBase;

        this.connected  = false;
        this.cnx        = null;
    }


    public boolean isConnected() {
        return connected;
    }

    public java.sql.Connection getConnection() {
        if(isConnected()) {
            return this.cnx;
        }

        try {
            Class.forName(this.sgbd.getDriverName());

            String url     = this.sgbd.getProtocol() + this.host + ":" + this.port + "/" + this.dataBase;
            this.cnx       = DriverManager.getConnection(url, this.user.getName(), this.user.getPassword());
            this.connected = true;

            this.cnx.setAutoCommit(false);
            return this.cnx;
        } catch(SQLException | ClassNotFoundException exception) {
            this.connected = false;
            throw new RuntimeException(exception.getMessage());
        }
    }

    public java.sql.Connection getConnection(boolean forceNew) {
        if(forceNew && isConnected()) {
            close();
        }

        return getConnection();
    }

    public boolean close() {
        if(isConnected()) {
            try {
                this.getConnection().close();
                this.cnx       = null;
                this.connected = false;
            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }

    public java.sql.Connection reset() {
        close();
        return getConnection();
    }

    public void endTransaction(boolean sucess) throws SQLException {
        if(sucess) {
            getConnection().commit();
            return;
        }
        getConnection().rollback();
    }

}