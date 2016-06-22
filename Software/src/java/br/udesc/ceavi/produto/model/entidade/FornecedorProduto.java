/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.entidade;

import br.udesc.ceavi.caixeiro.model.Usuario;

/**
 *
 * @author ignoi
 */
public class FornecedorProduto {

    private int id;
    private Usuario fornecedor;
    private Produto produto;
    private int valor;

    public FornecedorProduto() {
    }

    public FornecedorProduto(Usuario fornecedor, Produto produto, int valor) {
        this.fornecedor = fornecedor;
        this.produto = produto;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return fornecedor;
    }

    public void setUsuario(Usuario fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
