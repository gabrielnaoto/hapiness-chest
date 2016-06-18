package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.dao.iDaoVeiculo;
import br.udesc.ceavi.caixeiro.model.Veiculo;
import br.udesc.ceavi.core.model.dao.DAOGeneric;

/**
 *
 * @author Samuel Felício Adriano
 */
public class JDBCDAOVeiculo extends DAOGeneric<Veiculo> implements iDaoVeiculo {

    @Override
    public Veiculo getNewEntity() {
        return new Veiculo();
    }

}