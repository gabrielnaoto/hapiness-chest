/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Fornecedor;
import br.udesc.ceavi.caixeiro.model.dao.iDaoFornecedor;
import br.udesc.ceavi.core.model.dao.DAOGeneric;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAOFornecedor extends DAOGeneric<Fornecedor> implements iDaoFornecedor {

    @Override
    public Fornecedor getNewEntity() {
        return new Fornecedor();
    }


}
