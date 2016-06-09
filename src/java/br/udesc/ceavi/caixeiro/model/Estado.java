package caixeiro.model;

import core.model.Entity;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
public class Estado extends Entity<Estado> {

    private int id;
    private String descricao;

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

}//end Estado