package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.INT_INTEGER;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:50:58
 */
@Table(schema = "PRODUTO", name = "cesta")
public class Cesta extends Entity {

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "trimestre", dataType = INT_INTEGER)
    private int trimestre;

    @DataBaseInfo(columnName = "ano", dataType = INT_INTEGER)
    private int ano;

    @DataBaseInfo(columnName = "peso", dataType = EntityDataBaseTypeRelation.DOUBLE_NUMERIC)
    private double peso;

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

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        if (trimestre + ano > 0) {
            return trimestre + "/" + ano;
        }
        return null;
    }


}//end Cesta