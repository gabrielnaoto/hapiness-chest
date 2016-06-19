/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ignoi
 */
public class Conexao {

    public static class Database {

        String protocol;
        String driver;
        int port;

        public Database(String protocol, String driver, int port) {
            this.protocol = protocol;
            this.driver = driver;
            this.port = port;
        }

    }

    public static final int POSTGRES = 1;
    public static final int MYSQL = 2;

    private static Database db;
    private static String mydatabase = "bau";
    private static String username = "postgres";
    private static String password = "root";
    private static int database = 0;

    public static String status = "Não inicializado.";

    public static java.sql.Connection getConexao(int banco) {
        database = banco;
        switch (banco) {
            case 1: {
                db = new Database("jdbc:mysql://", "com.mysql.jdbc.Driver", 3306);
            }
            case 2: {
                db = new Database("jdbc:postgresql://", "org.postgresql.Driver", 5432);
            }
        }
        Connection connection = null;
        try {
            String drivername = db.driver;
            Class.forName(drivername);
            String servername = "localhost:" + db.port;
            String url = db.protocol + servername + "/" + mydatabase;
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                status = ("Conectado com sucesso!");
            } else {
                status = ("Falha ao conectar!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("O driver especificado não foi encontrado.");
            return null;
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar-se ao banco de dados.");
            return null;
        }
    }

    public static String getStatus() {
        return status;
    }

    public static boolean fechar() {
        try {
            Conexao.getConexao(database).close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static java.sql.Connection reiniciar() {
        Conexao.fechar();
        return Conexao.getConexao(database);
    }

}
