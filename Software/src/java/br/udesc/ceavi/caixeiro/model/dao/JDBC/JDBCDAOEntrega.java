/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEntrega;
import br.udesc.ceavi.core.model.dao.DAOGeneric;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public class JDBCDAOEntrega  extends DAOGeneric<Entrega> implements iDaoEntrega {

    @Override
    public Entrega getNewEntity() {
        return new Entrega();
    }

}
