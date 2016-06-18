/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.entidade;

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
    //tem uma data no eap? alguém me diz pq?
    
    public Produto() {
    }

    public Produto(int id, String descricao, double peso, double valor, double satisfacao) {
        this.id = id;
        this.descricao = descricao;
        this.peso = peso;
        this.valor = valor;
        this.satisfacao = satisfacao;
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

}
