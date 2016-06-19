package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Fornecedor;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.persistence.Persistible;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

/**
 *
 * @author Samuel Felício Adriano
 */
@RequestScoped
@ManagedBean
public class BeanFornecedor extends BeanEntity<Fornecedor> {

    @Override
    protected Persistible<Fornecedor> getDao() {
        return br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory.getDaoFornecedor();
    }

    public DataModel<Fornecedor> getListaFornecedor() {
        return this.getDataModelList();
    }

}