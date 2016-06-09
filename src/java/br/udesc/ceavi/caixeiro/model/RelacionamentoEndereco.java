package caixeiro.model;

import core.model.Entity;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
public class RelacionamentoEndereco extends Entity<RelacionamentoEndereco> {

    private int distancia;
    private Endereco EnderecoSaida;
    private Endereco EnderecoEntrada;

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Endereco getEnderecoSaida() {
        return EnderecoSaida;
    }

    public void setEnderecoSaida(Endereco EnderecoSaida) {
        this.EnderecoSaida = EnderecoSaida;
    }

    public Endereco getEnderecoEntrada() {
        return EnderecoEntrada;
    }

    public void setEnderecoEntrada(Endereco EnderecoEntrada) {
        this.EnderecoEntrada = EnderecoEntrada;
    }

}//end RelacionamentoEndereco