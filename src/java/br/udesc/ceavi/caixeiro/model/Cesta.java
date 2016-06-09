package caixeiro.model;

import core.model.Entity;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:50:58
 */
public class Cesta extends Entity<Cesta> {

    private int id;
    private float peso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

}//end Cesta