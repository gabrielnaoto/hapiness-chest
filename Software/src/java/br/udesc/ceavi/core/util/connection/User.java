package br.udesc.ceavi.core.util.connection;

/**
 * Usuário do Banco de Dados.
 *
 * @author Samuel Felício Adriano
 */
public class User {

    private String name;
    private String password;


    public User() {
        this(null, null);
    }

    public User(String name, String password) {
        setName(name);
        setPassword(password);
    }


    /**
     * Devolve o nome do Usuário a se conectar ao banco de dados.
     *
     * @return string name - nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do Usuário a se conectar ao banco de dados.
     *
     * @param name - nome do usuário.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devolve a senha do Usuário a se conectar ao banco de dados.
     *
     * @return string password - senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do Usuário a se conectar ao banco de dados.
     *
     * @param password - senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}