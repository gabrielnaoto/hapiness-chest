package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.*;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 * @author wagner
 * @version 1.0
 * @created 04-jun-2016 09:50:57
 */
@Table(schema = "CAIXEIRO", name = "centrodistribuicao")
public class CentroDistribuicao extends Entity {

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "nome", dataType = STRING_VARCHAR)
    private String nome;

    @DataBaseInfo(columnName = "endereco_id", dataType = INT_INTEGER)
    private Endereco Endereco;

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
        if(Endereco == null) {
            Endereco = new Endereco();
        }
        return Endereco;
    }

    public void setEndereco(Endereco Endereco) {
        this.Endereco = Endereco;
    }

}//end CentroDistribuicao