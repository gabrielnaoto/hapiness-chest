/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.EntregaIteracao;
import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEntregaIteracao;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import br.udesc.ceavi.core.model.dao.EntityIterable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAOEntregaIteracao  extends DAOGeneric<EntregaIteracao> implements iDaoEntregaIteracao {

    @Override
    public EntregaIteracao getNewEntity() {
        return new EntregaIteracao();
    }



    /**
     *
     * @param entrega
     * @return
     */
    @Override
    public Iterable<EntregaIteracao> getIretacaoEntrega(Entrega entrega) {
        try {
            String query = "select * from " + getTableNameComplete() +
                           " where entrega_id = ?" +
                           "   and id in (select min(sub.id) id from caixeiro.entrega_iteracao sub where sub.entrega_id = ? group by sub.id / 100)" +
                           " order by id";

            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setInt(1, entrega.getId());
            statement.setInt(2, entrega.getId());

            return new EntityIterable<>(this, statement);
        } catch(SQLException exception) {
            catchSQLException(exception);
            return null;
        }
    }

}
