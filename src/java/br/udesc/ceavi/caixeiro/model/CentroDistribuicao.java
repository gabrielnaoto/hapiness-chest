package caixeiro.model;

import core.model.Entity;

/**
 * @author wagner
 * @version 1.0
 * @created 04-jun-2016 09:50:57
 */
public class CentroDistribuicao extends Entity<CentroDistribuicao> {

    private int id;
    private Cliente Cliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente Cliente) {
        this.Cliente = Cliente;
    }

}//end CentroDistribuicao