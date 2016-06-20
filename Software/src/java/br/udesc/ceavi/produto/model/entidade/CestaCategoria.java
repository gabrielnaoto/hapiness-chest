/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.entidade;

/**
 *
 * @author ignoi
 */
public class CestaCategoria {

    private int id;
    private Cesta cesta;
    private Categoria categoria;

    public CestaCategoria() {
    }

    public CestaCategoria(int id, Cesta cesta, Categoria categoria) {
        this.id = id;
        this.cesta = cesta;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cesta getCesta() {
        return cesta;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
