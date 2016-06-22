/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoRelacionamentoEndereco;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import static br.udesc.ceavi.core.model.dao.DAOGeneric.OPERATOR_EQUAL;
import br.udesc.ceavi.core.model.dao.EntityIterable;
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

    @Override
    public Integer getQuantidadeEntrega(Entrega entrega) {
        try {
            String query = "with enderecos as (" + getSubSqlEnderecoEntrega() + ") " +
                           "select count(distinct id) quantidade from enderecos";

            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setInt(1, entrega.getId());
            statement.setInt(2, entrega.getId());

            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("quantidade");
        } catch(SQLException exception) {
            catchSQLException(exception);
            return null;
        }
    }

    /**
     *
     * @param entrega
     * @return
     */
    @Override
    public Iterable<RelacionamentoEndereco> getEnderecosEntrega(Entrega entrega) {
        try {
            String query = "with enderecos as (" + getSubSqlEnderecoEntrega() + "),\n" +
                           "registros as (select a.id endereco_saida_id, " +
                           "                     b.id endereco_chegada_id, " +
                           "                     coalesce(r1.distancia, r2.distancia) distancia, " +
                           "                     a.indice " +
                           "                from enderecos a\n" +
                           "  	 	   cross join enderecos b\n" +
                           "   		    left join caixeiro.relacionamento_endereco r1\n" +
                           "		      on a.id = r1.endereco_saida_id\n" +
                           "		     and b.id = r1.endereco_chegada_id\n" +
                           "		    left join caixeiro.relacionamento_endereco r2\n" +
                           "		      on b.id = r2.endereco_saida_id\n" +
                           "		     and a.id = r2.endereco_chegada_id\n" +
                           "		   where a.id <> b.id)\n" +
                           " select * from registros " +
                           "  order by indice";

            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setInt(1, entrega.getId());
            statement.setInt(2, entrega.getId());

            return new EntityIterable<RelacionamentoEndereco>(this, statement);
        } catch(SQLException exception) {
            catchSQLException(exception);
            return null;
        }
    }

    private String getSubSqlEnderecoEntrega() {
        return "select cliente.endereco_id id, count(*) quantidade\n, 0 as indice" +
               "  from produto.cliente\n" +
               "  join produto.carne on cliente_id = cliente.id\n" +
               "  join produto.pagamento on carne_id = carne.id\n" +
               "  join caixeiro.entrega_pagamento on pagamento_id = pagamento.id\n" +
               " where entrega_id = ?\n" +
               " group by endereco_id" +
               " union " +
               "select centrodistribuicao.endereco_id, 1, 1 as indice \n" +
               "  from caixeiro.centrodistribuicao \n" +
               "  join caixeiro.entrega on entrega.centrodistribuicao_id = centrodistribuicao.id\n" +
               " where entrega.id = ?";
    }

}
