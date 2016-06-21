package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.INT_INTEGER;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.STRING_VARCHAR;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:50:58
 */
@Table(schema = "PRODUTO", name = "cliente")
public class Cliente extends Entity {

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "nome", dataType = STRING_VARCHAR)
    private String nome;

    @DataBaseInfo(columnName = "endereco_id", dataType = INT_INTEGER)
    private Endereco Endereco;

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

    public Endereco getEndereco() {
        if (Endereco == null) {
            Endereco = new Endereco();
        }
        return Endereco;
    }

    public void setEndereco(Endereco Endereco) {
        this.Endereco = Endereco;
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