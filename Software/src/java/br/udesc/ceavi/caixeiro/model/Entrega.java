package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.INT_INTEGER;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.STRING_VARCHAR;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
@Table(schema = "CAIXEIRO", name = "entrega")
public class Entrega extends Entity {

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "data", dataType = STRING_VARCHAR)
    private String data;

    @DataBaseInfo(columnName = "centrodistribuicao_id", dataType = INT_INTEGER)
    private CentroDistribuicao CentroDistribuicao;

    @DataBaseInfo(columnName = "cesta_id", dataType = INT_INTEGER)
    private Cesta Cesta;

    @DataBaseInfo(columnName = "veiculo_id", dataType = INT_INTEGER)
    private Veiculo Veiculo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        if(data == null) {
            setData(new String());
        }

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CentroDistribuicao getCentroDistribuicao() {
        return CentroDistribuicao;
    }

    public void setCentroDistribuicao(CentroDistribuicao CentroDistribuicao) {
        this.CentroDistribuicao = CentroDistribuicao;
    }

    public Cesta getCesta() {
        return Cesta;
    }

    public void setCesta(Cesta Cesta) {
        this.Cesta = Cesta;
    }

    public Veiculo getVeiculo() {
        return Veiculo;
    }

    public void setVeiculo(Veiculo Veiculo) {
        this.Veiculo = Veiculo;
    }

}//end Entrega