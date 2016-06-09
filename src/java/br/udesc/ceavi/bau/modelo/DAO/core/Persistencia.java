/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.DAO.core;

import br.udesc.ceavi.bau.modelo.DAO.categoria.CategoriaDAO;
import br.udesc.ceavi.bau.modelo.DAO.cesta.CestaDAO;
import br.udesc.ceavi.bau.modelo.DAO.jdbc.JDBCFactory;
import br.udesc.ceavi.bau.modelo.DAO.produto.ProdutoDAO;

/**
 *
 * @author ignoi
 */
public abstract class Persistencia {

    public final static int JDBC = 1;

    public static Persistencia getPersistencia(int tipo) {
        if (tipo == JDBC) {
            return new JDBCFactory();
        }
        return null;
    }

    public abstract CestaDAO getCestaDAO();

    public abstract CategoriaDAO getCategoriaDAO();

    public abstract ProdutoDAO getProdutoDAO();

}
