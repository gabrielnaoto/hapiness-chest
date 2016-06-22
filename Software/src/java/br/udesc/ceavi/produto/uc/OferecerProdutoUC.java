/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.uc;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.PersistenceType;
import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.dao.cestaproduto.CestaProdutoDAO;
import br.udesc.ceavi.produto.model.dao.fornecedorproduto.FornecedorProdutoDAO;
import br.udesc.ceavi.produto.model.dao.produto.ProdutoDAO;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.model.entidade.CestaProduto;
import br.udesc.ceavi.produto.model.entidade.FornecedorProduto;
import br.udesc.ceavi.produto.model.entidade.Produto;
import java.util.List;

/**
 * - Apenas fornecedores podem oferecer produtos
 *
 * - Para calculo da cesta, os valores dos produtos lancados devem ser
 * arredondados pra cima e serem tratados como inteiro
 *
 * @author ignoi
 */
public class OferecerProdutoUC {

    private ProdutoDAO pdao;
    private FornecedorProdutoDAO fpdao;
    private CestaProdutoDAO cpdao;
    private CestaDAO cdao;
    private CategoriaDAO ctdao;
    private CestaDAO csdao;

    public OferecerProdutoUC() {
        pdao = Persistence.getPersistence(PersistenceType.JDBC).getProdutoDAO();
        fpdao = Persistence.getPersistence(PersistenceType.JDBC).getFornecedorProdutoDAO();
        cpdao = Persistence.getPersistence(PersistenceType.JDBC).getCestaProdutoDAO();
        cdao = Persistence.getPersistence(PersistenceType.JDBC).getCestaDAO();
        ctdao = Persistence.getPersistence(PersistenceType.JDBC).getCategoriaDAO();
        csdao = Persistence.getPersistence(PersistenceType.JDBC).getCestaDAO();
    }

    public boolean oferecer(Produto p, Usuario u, double valor) {
        int i = (int) Math.ceil(valor);
        boolean a = pdao.inserir(p);
        FornecedorProduto fp = new FornecedorProduto(u, p, i);
        boolean b = fpdao.inserir(fp);
        CestaProduto cp = new CestaProduto(cdao.getAtual(), p, valor);
        boolean c = cpdao.inserir(cp);
        return a && b && c;
    }
    
    public List<Produto> obterProdutos(Usuario u){
        return pdao.listarPorFornecedor(u.getId());
    }
    
    public List<Categoria> obterCategorias(){
        return ctdao.getFromCesta(csdao.getAtual().getId());
    }

}
