/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.DAO;

import br.udesc.ceavi.bau.modelo.DAO.core.Persistencia;
import br.udesc.ceavi.bau.modelo.DAO.jdbc.JDBCFactory;
import br.udesc.ceavi.bau.modelo.entidade.Categoria;

/**
 *
 * @author sila
 */
public class TesteDAO {

    public static void main(String[] args) {
        JDBCFactory f = (JDBCFactory) Persistencia.getPersistencia(1);

        Categoria c = new Categoria(1, "descricao");

        f.getCategoriaDAO().deletar(1);
        f.getCategoriaDAO().inserir(c);

        c = f.getCategoriaDAO().pesquisar(1);
        System.out.println(c.getDescricao());
        c.setDescricao("nada");
        f.getCategoriaDAO().atualizar(c);
        c = f.getCategoriaDAO().pesquisar(1);
        System.out.println(c.getDescricao());
    }
}
