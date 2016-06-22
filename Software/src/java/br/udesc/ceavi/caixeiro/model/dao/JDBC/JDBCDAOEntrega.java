/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEntrega;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import br.udesc.ceavi.core.model.dao.EntityIterable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAOEntrega  extends DAOGeneric<Entrega> implements iDaoEntrega {

    @Override
    public Entrega getNewEntity() {
        return new Entrega();
    }

    /**
     *
     * @param entrega
     */
    @Override
    public void separaPagamentos(Entrega entrega) {
        execute("delete from caixeiro.entrega_pagamento where entrega_id = " + entrega.getId());
        execute("delete from caixeiro.entrega_iteracao where entrega_id = " + entrega.getId());

        String comando = "insert into caixeiro.entrega_pagamento (entrega_id, pagamento_id) " +
                         "select " + entrega.getId() + ", pagamento.id from produto.endereco \n" +
                         "  join produto.cliente on cliente.endereco_id = endereco.id\n" +
                         "  join produto.carne on carne.cliente_id = cliente.id\n" +
                         "  join produto.pagamento on pagamento.carne_id = carne.id\n" +
                         "  and not exists (select 1 from caixeiro.entrega_pagamento sub_pag\n" +
                         "                   where sub_pag.pagamento_id = pagamento.id)\n" +
                         " order by data\n" +
                         " limit (select floor(capacidade / peso) \n" +
                         "          from caixeiro.entrega \n" +
                         "          join caixeiro.veiculo on entrega.veiculo_id = veiculo.id\n" +
                         "          join produto.cesta on entrega.cesta_id = cesta.id\n" +
                         "         where entrega.id = " + entrega.getId() + ")";
        execute(comando);
    }

    @Override
    public String getTrajeto(Entrega entrega) {
        try {
            String query = "select trajeto from caixeiro.entrega_iteracao" +
                           " where entrega_id = ?" +
                           " order by id desc limit 1";

            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setInt(1, entrega.getId());

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return result.getString("trajeto");
            }
        } catch(SQLException exception) {
            catchSQLException(exception);
        }
        return null;
    }

    @Override
    public int getQtdeIteracao(Entrega entrega) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
