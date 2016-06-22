/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.DOUBLE_NUMERIC;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.INT_INTEGER;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.STRING_VARCHAR;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;
import java.io.Serializable;

/**
 *
 * @author Ricardo Augusto Küstner
 */
@Table(schema = "CAIXEIRO", name = "entrega_iteracao")
public class EntregaIteracao extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @DataBaseInfo(key = true, columnName = "id", dataType = EntityDataBaseTypeRelation.LONG_BIGINT, sequential = true)
    private long id;

    @DataBaseInfo(columnName = "entrega_id", dataType = INT_INTEGER)
    private Entrega entrega;

    @DataBaseInfo(columnName = "distancia", dataType = DOUBLE_NUMERIC)
    private double distancia;

    @DataBaseInfo(columnName = "media", dataType = DOUBLE_NUMERIC)
    private double media;

    @DataBaseInfo(columnName = "trajeto", dataType = STRING_VARCHAR)
    private String trajeto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Entrega getEntrega() {
        if (entrega == null) {
            entrega = new Entrega();
        }
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public String getTrajeto() {
        return trajeto;
    }

    public void setTrajeto(String trajeto) {
        this.trajeto = trajeto;
    }
}
