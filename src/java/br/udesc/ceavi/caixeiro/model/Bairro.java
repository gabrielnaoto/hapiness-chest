package caixeiro.model;

import core.model.Entity;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:50:57
 */
public class Bairro extends Entity<Bairro> {

    private int id;
    private String decricao;
    private Cidade Cidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public Cidade getCidade() {
        return Cidade;
    }

    public void setCidade(Cidade Cidade) {
        this.Cidade = Cidade;
    }

}//end Bairro