package caixeiro.model;

import core.model.Entity;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
public class Endereco extends Entity<Endereco> {

    private int id;
    private int numero;
    private String complemento;
    private Logradouro Logradouro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Logradouro getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(Logradouro Logradouro) {
        this.Logradouro = Logradouro;
    }

}//end Endereco