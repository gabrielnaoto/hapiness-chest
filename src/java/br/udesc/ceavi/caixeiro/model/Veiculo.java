package caixeiro.model;

import core.model.Entity;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
public class Veiculo extends Entity<Veiculo> {

    private int id;
    private String descricao;
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
    
}//end Veiculo