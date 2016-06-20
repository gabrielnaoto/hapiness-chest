/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.uc;

import br.udesc.ceavi.core.persistence.Persistence;
import static br.udesc.ceavi.core.persistence.PersistenceType.JDBC;
import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.dao.cestacategoria.CestaCategoriaDAO;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.model.entidade.Cesta;
import br.udesc.ceavi.produto.model.entidade.CestaCategoria;
import java.util.List;

/**
 *
 * - Administrador deve cadastrar categorias para o leilao, validar se essas
 * categorias nao foram utilizadas no ultimo ano.
 *
 * - Administrador deve informar um lucro para a cesta, assim, deve ser feito o
 * calculo: [(pagantes x (valorCarne x 3)) * (1 - lucro)] / pagantes.
 *
 * @author ignoi
 */
public class GerenciarCestaUC {

    private CestaDAO cestaDAO;
    private CategoriaDAO categoriaDAO;
    private CestaCategoriaDAO ccDAO;

    public GerenciarCestaUC() {
        ccDAO = Persistence.getPersistence(JDBC).getCestaCategoriaDAO();
        cestaDAO = Persistence.getPersistence(JDBC).getCestaDAO();
        categoriaDAO = Persistence.getPersistence(JDBC).getCategoriaDAO();
    }

    public boolean criar(Cesta c) {
        boolean inseriu = cestaDAO.inserir(c);
        try {
            List<Categoria> categorias = c.getCategorias();
            for (Categoria categoria : categorias) {
                categoriaDAO.inserir(categoria);
                CestaCategoria cc = new CestaCategoria();
                cc.setCesta(c);
                cc.setCategoria(categoria);
                ccDAO.inserir(cc);
            }

        } catch (Exception e) {
            System.out.println("n deu boa n");
        }
        return inseriu;
    }

    public List<Cesta> obterCestas() {
        return cestaDAO.listarFinalizadas();
    }

    public Cesta getAtual() {
        Cesta atual = cestaDAO.getAtual();
        atual.setCategorias(categoriaDAO.getFromCesta(atual.getId()));
        return atual;
    }

}
