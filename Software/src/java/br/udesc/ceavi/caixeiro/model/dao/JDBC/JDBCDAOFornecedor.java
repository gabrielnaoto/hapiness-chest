/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Fornecedor;
import br.udesc.ceavi.caixeiro.model.dao.iDaoFornecedor;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import static br.udesc.ceavi.core.model.dao.DAOGeneric.OPERATOR_EQUAL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAOFornecedor extends DAOGeneric<Fornecedor> implements iDaoFornecedor {

    @Override
    public Fornecedor getNewEntity() {
        return new Fornecedor();
    }

    @Override
    public boolean isNomeCadastrado(String nome) {
        StringBuilder query = new StringBuilder("SELECT *");
        query.append("\nFROM ")
                .append(this.getTableNameComplete())
                .append("\nWHERE TRUE")
                .append("\nAND nome " + OPERATOR_EQUAL + " ?");

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

            statement.setString(1, nome);

            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

}
