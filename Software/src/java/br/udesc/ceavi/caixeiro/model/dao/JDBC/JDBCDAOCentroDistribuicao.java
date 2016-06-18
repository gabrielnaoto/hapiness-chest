package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.CentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCentroDistribuicao;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import br.udesc.ceavi.core.model.dao.Relation;
import br.udesc.ceavi.core.util.BeanUtils;
import br.udesc.ceavi.core.util.StringUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel Felício Adriano
 */
public class JDBCDAOCentroDistribuicao extends DAOGeneric<CentroDistribuicao> implements iDaoCentroDistribuicao {

    @Override
    public CentroDistribuicao getNewEntity() {
        return new CentroDistribuicao();
    }

    @Override
    protected boolean runInsert(CentroDistribuicao entity) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(this.getTableNameComplete()).
                      append(" (").
                      append(String.join(", ", this.relationships.getAllNonSequentialsColumnsNames())).
                      append(") VALUES (").
                      append(String.join(", ", relationships.getAllNonSequentialsPreparedParameters())).
                      append(") RETURNING ").
                      append(String.join(", ", relationships.getAllColumnsNames()));

        PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

        for(int i = 0; i < relationships.getAllNonSequentialsTypes().size(); i++) {
            if(relationships.getAllNonSequentialsColumnsNames().get(i).equals("endereco_id") &&
               (int) BeanUtils.callGetter(entity, StringUtils.toBeanFormat("endereco_id"), true) == 0) {
                statement.setObject(i + 1, null);
                continue;
            }
            setValueFromBeanIntoStatement(entity, statement, relationships.getAllNonSequentialsTypes().get(i), relationships.getAllNonSequentialsColumnsNames().get(i), i);
        }

        ResultSet result = statement.executeQuery();
        if(result.next()) {
            for(Relation relation : getRelationships().getAllRelations()) {
                BeanUtils.callSetter(entity, relation.getModelName(), getValueFromStatement(result, relation));
            }
            return true;
        }
        return false;
    }

}