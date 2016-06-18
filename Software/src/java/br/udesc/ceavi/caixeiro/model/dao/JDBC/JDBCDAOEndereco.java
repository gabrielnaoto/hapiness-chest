package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.core.model.dao.DAOGeneric;

/**
 *
 * @author Samuel Felício Adriano
 */
public class JDBCDAOEndereco extends DAOGeneric<Endereco> implements iDaoEndereco {

    @Override
    public Endereco getNewEntity() {
        return new Endereco();
    }

}