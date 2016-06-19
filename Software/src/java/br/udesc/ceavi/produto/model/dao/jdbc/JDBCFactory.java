/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.jdbc;

import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.dao.categoria.JDBCCategoriaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.JDBCCestaDAO;
import br.udesc.ceavi.produto.model.dao.core.Persistencia;
import br.udesc.ceavi.produto.model.dao.produto.JDBCProdutoDAO;
import br.udesc.ceavi.produto.model.dao.produto.ProdutoDAO;

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
