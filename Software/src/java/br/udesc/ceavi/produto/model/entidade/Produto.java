package br.udesc.ceavi.produto.model.entidade;

import br.udesc.ceavi.produto.model.entidade.Categoria;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ignoi
 */
public class Produto {

    private int id;
    private String descricao;
    private double peso;
    private double valor;
    private double satisfacao;
    private Categoria categoria;

    public Produto() {
    }

    public Produto(int id, String descricao, double peso, double valor, double satisfacao, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.peso = peso;
        this.valor = valor;
        this.satisfacao = satisfacao;
        this.categoria = categoria;
    }

    public Produto(int id, double valor, double satisfacao, Categoria categoria) {
        this.id = id;
        this.valor = valor;
        this.satisfacao = satisfacao;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(double satisfacao) {
        this.satisfacao = satisfacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", descricao=" + descricao + ", peso=" + peso + ", valor=" + valor + ", satisfacao=" + satisfacao + ", categoria=" + categoria + '}';
    }

}
