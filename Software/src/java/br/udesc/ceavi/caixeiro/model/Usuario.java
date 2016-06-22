package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.*;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 *
 * @author Samuel Felício Adriano
 */
@Table(schema = "PRODUTO", name = "usuario")
public class Usuario extends Entity {

    private static final long serialVersionUID = 1L;


    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "login", dataType = STRING_VARCHAR)
    private String login;

    @DataBaseInfo(columnName = "senha", dataType = STRING_VARCHAR)
    private String senha;

    @DataBaseInfo(columnName = "tipo", dataType = INT_INTEGER)
    private TipoUsuario tipo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipo() {
        return tipo.getTipo();
    }

    public void setTipo(int tipo) {
        this.tipo = TipoUsuario.getTipoUsuarioByTipo(tipo);
    }

}