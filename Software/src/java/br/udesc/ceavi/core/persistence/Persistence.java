package br.udesc.ceavi.core.persistence;

import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.dao.cestaproduto.CestaProdutoDAO;
import br.udesc.ceavi.produto.model.dao.clienteproduto.ClienteProdutoDAO;
import br.udesc.ceavi.produto.model.dao.produto.ProdutoDAO;
import br.udesc.ceavi.produto.model.fornecedorproduto.FornecedorProdutoDAO;

/**
 *
 * @author Samuel Felício Adriano
 */
public abstract class Persistence {

    public static final Persistence getPersistence(PersistenceType persistenceType) {
        switch (persistenceType) {
            case JDBC:
                return new br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory();
        }
        return null;
    }
//    public abstract iDaoCentroDistribuicao getDaoCentroDistribuicao();
//
//    public abstract iDaoCliente getDaoCliente();
//
//    public abstract iDaoEndereco getDaoEndereco();
//
//    public abstract iDaoEntrega getDaoEntrega();
//
//    public abstract iDaoVeiculo getDaoVeiculo();
//
//    public abstract iDaoRelacionamentoEndereco getDaoRelacionamentoEndereco();

    public abstract CestaDAO getCestaDAO();

    public abstract CategoriaDAO getCategoriaDAO();

    public abstract ProdutoDAO getProdutoDAO();

    public abstract ClienteProdutoDAO getClienteProdutoDAO();

    public abstract FornecedorProdutoDAO getFornecedorProdutoDAO();

    public abstract CestaProdutoDAO getCestaProdutoDAO();

}
