/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.entidade;

import br.udesc.ceavi.caixeiro.model.Cliente;
import br.udesc.ceavi.caixeiro.model.Usuario;

/**
 *
 * @author ignoi
 */
public class ClienteProduto {

    private int id;
    private Usuario usuario;
    private Produto produto;
    private int satisfacao;

    public ClienteProduto() {
    }

    public ClienteProduto(Usuario usuario, Produto produto, int satisfacao) {
        this.usuario = usuario;
        this.produto = produto;
        this.satisfacao = satisfacao;
    }

    public ClienteProduto(int id, Usuario usuario, Produto produto, int satisfacao) {
        this.id = id;
        this.usuario = usuario;
        this.produto = produto;
        this.satisfacao = satisfacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
