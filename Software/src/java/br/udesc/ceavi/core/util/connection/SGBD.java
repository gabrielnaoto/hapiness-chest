package br.udesc.ceavi.core.util.connection;

/**
 * Enum para especificar os SGBDs para realizar a conexão.
 *
 * @author Samuel Felício Adriano
 */
public enum SGBD {

     POSTGRE_SQL("org.postgresql.Driver", "jdbc:postgresql://");


    private final String driverName;
    private final String protocol;


    private SGBD(String driverName, String protocol) {
        this.driverName = driverName;
        this.protocol   = protocol;
    }


    public String getDriverName() {
        return driverName;
    }

    public String getProtocol() {
        return protocol;
    }

}