/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.dao.jdbc;

import br.udesc.ceavi.bau.modelo.dao.categoria.JDBCCategoriaDAO;
import br.udesc.ceavi.bau.modelo.dao.core.Persistencia;
import br.udesc.ceavi.bau.modelo.dao.produto.JDBCProdutoDAO;
import br.udesc.ceavi.cesta.modelo.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.cesta.modelo.dao.cesta.CestaDAO;
import br.udesc.ceavi.cesta.modelo.dao.cesta.JDBCCestaDAO;
import br.udesc.ceavi.cesta.modelo.dao.produto.ProdutoDAO;

/**
 *
 * @author ignoi
 */
public class JDBCFactory extends Persistencia {

    public JDBCFactory() {
    }
    
    @Override
    public CestaDAO getCestaDAO() {
        return new JDBCCestaDAO();
    }

    @Override
    public CategoriaDAO getCategoriaDAO() {
        return new JDBCCategoriaDAO();
    }

    @Override
    public ProdutoDAO getProdutoDAO() {
        return new JDBCProdutoDAO();
    }

}
