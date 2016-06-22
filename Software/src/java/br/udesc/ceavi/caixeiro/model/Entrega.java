package br.udesc.ceavi.caixeiro.model;

import br.udesc.ceavi.core.model.Entity;
import br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation;
import static br.udesc.ceavi.core.persistence.EntityDataBaseTypeRelation.INT_INTEGER;
import br.udesc.ceavi.core.persistence.annotation.DataBaseInfo;
import br.udesc.ceavi.core.persistence.annotation.Table;
import java.util.Date;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
@Table(schema = "CAIXEIRO", name = "entrega")
public class Entrega extends Entity {

    @DataBaseInfo(key = true, columnName = "id", dataType = INT_INTEGER, sequential = true)
    private int id;

    @DataBaseInfo(columnName = "data", dataType = EntityDataBaseTypeRelation.DATE_DATE)
    private Date data;

    @DataBaseInfo(columnName = "centrodistribuicao_id", dataType = INT_INTEGER)
    private CentroDistribuicao CentroDistribuicao;

    @DataBaseInfo(columnName = "cesta_id", dataType = INT_INTEGER)
    private Cesta cesta;

    @DataBaseInfo(columnName = "veiculo_id", dataType = INT_INTEGER)
    private Veiculo veiculo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        if (data == null) {
            data = new Date();
        }
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setData(java.sql.Date data) {
        this.data = data;
    }

    public CentroDistribuicao getCentroDistribuicao() {
        if (CentroDistribuicao == null) {
            CentroDistribuicao = new CentroDistribuicao();
        }
        return CentroDistribuicao;
    }

    public void setCentroDistribuicao(CentroDistribuicao CentroDistribuicao) {
        this.CentroDistribuicao = CentroDistribuicao;
    }

    /**
     * Mais um que vai pro inferno
     * @return
     */
    public CentroDistribuicao getCentrodistribuicao() {
        return getCentroDistribuicao();
    }

    /**
     * E não tem volta
     * @param CentroDistribuicao
     */
    public void setCentrodistribuicao(CentroDistribuicao CentroDistribuicao) {
        setCentroDistribuicao(CentroDistribuicao);
    }

    public Cesta getCesta() {
        if (cesta == null) {
            cesta = new Cesta();
        }
        return cesta;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    public Veiculo getVeiculo() {
        if (veiculo == null) {
            veiculo = new Veiculo();
        }
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

}//end Entrega