package br.udesc.ceavi.produto.model.entidade;

import java.util.Date;

public class CestaCategoria {

    private int id;
    private Cesta cesta;
    private Categoria categoria;
    private Date data;

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
