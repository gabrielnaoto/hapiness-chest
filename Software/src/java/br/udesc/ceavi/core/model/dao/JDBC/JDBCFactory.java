package br.udesc.ceavi.core.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.dao.iDaoCentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCliente;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEntrega;
import br.udesc.ceavi.caixeiro.model.dao.iDaoFornecedor;
import br.udesc.ceavi.caixeiro.model.dao.iDaoRelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoVeiculo;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOCentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOCliente;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOEndereco;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOEntrega;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOFornecedor;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAORelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOVeiculo;
import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.Persistible;
import br.udesc.ceavi.core.util.ClassUtils;
import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.dao.categoria.JDBCCategoriaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.dao.cesta.JDBCCestaDAO;
import br.udesc.ceavi.produto.model.dao.cestaproduto.CestaProdutoDAO;
import br.udesc.ceavi.produto.model.dao.cestaproduto.JDBCCestaProdutoDAO;
import br.udesc.ceavi.produto.model.dao.clienteproduto.ClienteProdutoDAO;
import br.udesc.ceavi.produto.model.dao.clienteproduto.JDBCClienteProdutoDAO;
import br.udesc.ceavi.produto.model.dao.produto.JDBCProdutoDAO;
import br.udesc.ceavi.produto.model.dao.produto.ProdutoDAO;
import br.udesc.ceavi.produto.model.fornecedorproduto.FornecedorProdutoDAO;
import br.udesc.ceavi.produto.model.fornecedorproduto.JDBCFornecedorProdutoDAO;
import java.util.HashMap;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
public class JDBCFactory extends Persistence {

    private static HashMap<String, Persistible> daos = new HashMap<>();

    public static iDaoCentroDistribuicao getDaoCentroDistribuicao() {
        return (iDaoCentroDistribuicao) load(JDBCDAOCentroDistribuicao.class);
    }

//    public static iDaoCesta getDaoCesta() {
//        return (iDaoCesta) load(JDBCDAOCesta.class);
//    } não tem daocesta ainda

    public static iDaoCliente getDaoCliente() {
        return (iDaoCliente) load(JDBCDAOCliente.class);
    }

    public static iDaoEndereco getDaoEndereco() {
        return (iDaoEndereco) load(JDBCDAOEndereco.class);
    }

    public static iDaoEntrega getDaoEntrega() {
        return (iDaoEntrega) load(JDBCDAOEntrega.class);
    }

    public static iDaoVeiculo getDaoVeiculo() {
        return (iDaoVeiculo) load(JDBCDAOVeiculo.class);
    }

    public static iDaoRelacionamentoEndereco getDaoRelacionamentoEndereco() {
        return (iDaoRelacionamentoEndereco) load(JDBCDAORelacionamentoEndereco.class);
    }

    public static iDaoFornecedor getDaoFornecedor() {
        return (iDaoFornecedor) load(JDBCDAOFornecedor.class);
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

    @Override
    public ClienteProdutoDAO getClienteProdutoDAO() {
        return new JDBCClienteProdutoDAO();
    }

    @Override
    public FornecedorProdutoDAO getFornecedorProdutoDAO() {
        return new JDBCFornecedorProdutoDAO();
    }

    @Override
    public CestaProdutoDAO getCestaProdutoDAO() {
        return new JDBCCestaProdutoDAO();
    }

    /**
     * Load an instance of the required class
     *
     * @param aClass
     * @return
     */
    private static Persistible load(Class<? extends Persistible> aClass) {
        if (!daos.containsKey(aClass.getName())) {
            daos.put(aClass.getName(), ClassUtils.getNewPOJO(aClass));
        }

        return daos.get(aClass.getName());
    }

}//end JDBCFactory
