package caixeiro.model;

import core.model.Entity;
import java.util.Date;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
public class Entrega extends Entity<Entrega> {

    private int id;
    private Date data;
    private CentroDistribuicao CentroDistribuicao;
    private Cesta Cesta;
    private Veiculo Veiculo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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