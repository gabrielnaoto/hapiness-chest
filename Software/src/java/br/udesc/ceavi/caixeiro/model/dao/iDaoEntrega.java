package br.udesc.ceavi.caixeiro.model.dao;
import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.core.persistence.Persistible;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:51:02
 */
public interface iDaoEntrega extends Persistible<Entrega> {

    public void separaPagamentos(Entrega entrega);

    public String getTrajeto(Entrega entrega);

    public int getQtdeIteracao(Entrega entrega);

}