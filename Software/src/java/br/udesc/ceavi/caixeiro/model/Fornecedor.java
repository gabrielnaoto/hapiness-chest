package br.udesc.ceavi.caixeiro.model;

import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.*;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 *
 * @author Samuel Felício Adriano
 * @since  19/06/2016
 */
@Table(schema = "PRODUTO", name = "fornecedor")
public class Fornecedor extends br.udesc.ceavi.core.model.Entity {

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "nome", dataType = STRING_VARCHAR)
    private String nome;

    @DataBaseInfo(columnName = "usuario_id", dataType = INT_INTEGER)
    private Usuario usuario;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   public Usuario getUsuario() {
        if(usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}