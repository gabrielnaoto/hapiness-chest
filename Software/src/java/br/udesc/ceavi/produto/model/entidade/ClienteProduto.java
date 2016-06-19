/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.entidade;

import br.udesc.ceavi.caixeiro.model.Cliente;

/**
 *
 * @author ignoi
 */
public class ClienteProduto {

    private int id;
    private Cliente cliente;
    private Produto produto;
    private int satisfacao;

    public ClienteProduto() {
    }

    public ClienteProduto(int id, Cliente cliente, Produto produto, int satisfacao) {
        this.id = id;
        this.cliente = cliente;
        this.produto = produto;
        this.satisfacao = satisfacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(int satisfacao) {
        this.satisfacao = satisfacao;
    }

}
