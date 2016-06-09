/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.DAO.jdbc;

import br.udesc.ceavi.bau.modelo.DAO.categoria.CategoriaDAO;
import br.udesc.ceavi.bau.modelo.DAO.categoria.JDBCCategoriaDAO;
import br.udesc.ceavi.bau.modelo.DAO.cesta.CestaDAO;
import br.udesc.ceavi.bau.modelo.DAO.cesta.JDBCCestaDAO;
import br.udesc.ceavi.bau.modelo.DAO.core.Persistencia;
import br.udesc.ceavi.bau.modelo.DAO.produto.JDBCProdutoDAO;
import br.udesc.ceavi.bau.modelo.DAO.produto.ProdutoDAO;

/**
 *
 * @author ignoi
 */
public class JDBCFactory extends Persistencia {

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
