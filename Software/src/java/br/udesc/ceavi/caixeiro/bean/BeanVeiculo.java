package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Veiculo;
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
public class BeanVeiculo extends BeanEntity<Veiculo> {

    @Override
    protected Persistible<Veiculo> getDao() {
        return br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory.getDaoVeiculo();
    }

    public DataModel<Veiculo> getListaVeiculo() {
        return this.getDataModelList();
    }

}