/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoRelacionamentoEndereco;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import static br.udesc.ceavi.core.model.dao.DAOGeneric.OPERATOR_EQUAL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAORelacionamentoEndereco extends DAOGeneric<RelacionamentoEndereco> implements iDaoRelacionamentoEndereco {

    @Override
    public RelacionamentoEndereco getNewEntity() {
        return new RelacionamentoEndereco();
    }

    @Override
    public boolean insert(RelacionamentoEndereco entity) {
        StringBuilder query = new StringBuilder();
        query.append("insert into ")
             .append(getTableNameComplete())
             .append("(endereco_saida_id, endereco_chegada_id, distancia)")
             .append("values (")
             .append(entity.getEnderecoSaida().getId() + ", ")
             .append(entity.getEnderecoChegada().getId() + ", ")
             .append("EXTRACT(EPOCH FROM replace(replace(replace('" + entity.getTempo() + "', 'horas', 'hours'), 'hora', 'hour'), 'minuto', 'minute')::INTERVAL) / 60")
             .append(")");

        return execute(query.toString());
    }

    @Override
    public boolean exists(RelacionamentoEndereco entity) {
        try {
            StringBuilder query = new StringBuilder("SELECT EXISTS(SELECT 1");
            query.append("\nFROM ")
                 .append(this.getTableNameComplete())
                 .append("\nWHERE TRUE");

            for(int i = 0; i < relationships.getAllKeyColumnsNames().size(); i++) {
                query.append("\nAND ").
                      append(relationships.getAllKeyColumnsNames().get(i)).
                      append(" " + OPERATOR_EQUAL + " ?");
            }
            query.append(")");

            PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

            statement.setInt(1, entity.getEnderecoSaida().getId());
            statement.setInt(2, entity.getEnderecoChegada().getId());

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return result.getString(1).equals("t");
            }
            return false;
        } catch(SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

    public ResultSet getAllEnderecoEntrega() {
        try {
            String query = "select a.id, b.id, distancia from produto.endereco a\n" +
                "            cross join produto.endereco b\n" +
                "             left join caixeiro.relacionamento_endereco\n" +
                "               on a.id = endereco_saida_id\n" +
                "              and b.id = endereco_chegada_id\n" +
                "            where a.id <> b.id\n" +
                "              and distancia is not null";

            PreparedStatement statement = connection.getConnection().prepareStatement(query);

            return statement.executeQuery();
        } catch(SQLException exception) {
            catchSQLException(exception);
            return null;
        }
    }

}
