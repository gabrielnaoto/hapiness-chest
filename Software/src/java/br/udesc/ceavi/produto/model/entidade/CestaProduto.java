package br.udesc.ceavi.produto.model.entidade;

public class CestaProduto {

    private int id;
    private Cesta cesta;
    private Produto produto;
    private double valor;

    public CestaProduto() {
    }

    public CestaProduto(Cesta cesta, Produto produto, double valor) {
        this.cesta = cesta;
        this.produto = produto;
        this.valor = valor;
    }

    public CestaProduto(int id, Cesta cesta, Produto produto, double valor) {
        this.id = id;
        this.cesta = cesta;
        this.produto = produto;
        this.valor = valor;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
