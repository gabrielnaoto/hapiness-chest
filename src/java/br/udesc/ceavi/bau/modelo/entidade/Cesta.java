/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.entidade;

import java.util.Date;

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

    public Cesta() {
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

}
