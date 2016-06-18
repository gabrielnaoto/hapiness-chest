package br.udesc.ceavi.core.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOCentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOCliente;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOEndereco;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOEntrega;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAORelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.JDBC.JDBCDAOVeiculo;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCliente;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEntrega;
import br.udesc.ceavi.caixeiro.model.dao.iDaoRelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoVeiculo;
import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.Persistible;
import br.udesc.ceavi.core.util.ClassUtils;
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

	public static iDaoCliente getDaoCliente(){
            return (iDaoCliente) load(JDBCDAOCliente.class);
	}

	public static iDaoEndereco getDaoEndereco() {
            return (iDaoEndereco) load(JDBCDAOEndereco.class);
	}

	public static iDaoEntrega getDaoEntrega(){
            return (iDaoEntrega) load(JDBCDAOEntrega.class);
	}

	public static iDaoVeiculo getDaoVeiculo(){
            return (iDaoVeiculo) load(JDBCDAOVeiculo.class);
	}

	public static iDaoRelacionamentoEndereco getDaoRelacionamentoEndereco(){
            return (iDaoRelacionamentoEndereco) load(JDBCDAORelacionamentoEndereco.class);
	}

        /**
         * Load an instance of the required class
         * @param aClass
         * @return
         */
        private static Persistible load(Class<? extends Persistible> aClass) {
            if(!daos.containsKey(aClass.getName())) {
                daos.put(aClass.getName(), ClassUtils.getNewPOJO(aClass));
            }

            return daos.get(aClass.getName());
        }

}//end JDBCFactory