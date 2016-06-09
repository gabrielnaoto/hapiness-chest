package caixeiro.model;

import core.model.Entity;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:50:58
 */
public class Cliente extends Entity<Cliente> {

    private int id;
    private String nome;
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
        return Endereco;
    }

    public void setEndereco(Endereco Endereco) {
        this.Endereco = Endereco;
    }
    
}//end Cliente