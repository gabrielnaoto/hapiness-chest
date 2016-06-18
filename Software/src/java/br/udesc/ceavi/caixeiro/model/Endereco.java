package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.*;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
@Table(schema = "PRODUTO", name = "endereco")
public class Endereco extends Entity {

    // Latitude e Longitude da UDESC
    public static final double LATITUDE_UDESC  = -27.0498958,
                               LONGITUDE_UDESC = -49.5400062;


    private static final long serialVersionUID = 2L;

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int    id;

    @DataBaseInfo(columnName = "latitude", dataType = DOUBLE_NUMERIC)
    private double latitude;

    @DataBaseInfo(columnName = "longitude", dataType = DOUBLE_NUMERIC)
    private double longitude;

    @DataBaseInfo(columnName = "descricao", dataType = STRING_VARCHAR)
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}//end Endereco