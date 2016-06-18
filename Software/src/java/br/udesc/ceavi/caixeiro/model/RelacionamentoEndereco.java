package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.INT_INTEGER;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
@Table(schema = "CAIXEIRO", name = "relacionamento_endereco")
public class RelacionamentoEndereco extends Entity {

    @DataBaseInfo(columnName = "distancia", dataType = INT_INTEGER)
    private int distancia;

    @DataBaseInfo(key = true, columnName = "endereco_saida_id", dataType = INT_INTEGER)
    private Endereco EnderecoSaida;

    @DataBaseInfo(key = true, columnName = "endereco_chegada_id", dataType = INT_INTEGER)
    private Endereco EnderecoChegada;

    private String tempo; // não vai pro banco

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

    public Endereco getEnderecoChegada() {
        return EnderecoChegada;
    }

    public void setEnderecoChegada(Endereco EnderecoChegada) {
        this.EnderecoChegada = EnderecoChegada;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}//end RelacionamentoEndereco