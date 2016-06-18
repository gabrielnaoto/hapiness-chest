package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.*;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;
import java.io.Serializable;

@Table(schema = "CAIXEIRO", name = "veiculo")
/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
public class Veiculo extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "descricao", dataType = STRING_VARCHAR)
    private String descricao;

    @DataBaseInfo(columnName = "capacidade", dataType = INT_INTEGER)
    private int capacidade;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public Veiculo clone() {
        return (Veiculo) super.clone();
    }

}