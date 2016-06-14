package br.udesc.ceavi.cesta.modelo.entidade;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ignoi
 */
public class Cesta {

    private int id;
    private double peso;
    private String tema;
    private Date data;
    private double valorMaximo;
    private List<Produto> produtos;

    public Cesta() {
        produtos = new ArrayList<Produto>();
    }

    public Cesta(int id, double peso, String tema, Date data, double valorMaximo) {
        this.id = id;
        this.peso = peso;
        this.tema = tema;
        this.data = data;
        this.valorMaximo = valorMaximo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    public void addProduto(Produto p){
        produtos.add(p);
    }

}
