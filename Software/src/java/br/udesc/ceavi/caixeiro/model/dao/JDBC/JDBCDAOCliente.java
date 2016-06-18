/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Cliente;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCliente;
import br.udesc.ceavi.core.model.dao.DAOGeneric;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAOCliente  extends DAOGeneric<Cliente> implements iDaoCliente {

    @Override
    public Cliente getNewEntity() {
        return new Cliente();
    }

}
