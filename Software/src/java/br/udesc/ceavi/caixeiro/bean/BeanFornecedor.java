package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Fornecedor;
import br.udesc.ceavi.caixeiro.model.dao.iDaoFornecedor;
import br.udesc.ceavi.caixeiro.model.dao.iDaoUsuario;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import br.udesc.ceavi.core.util.encryption.Md5Utils;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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

    public String cadastrar() {
        iDaoUsuario daoUsuario = JDBCFactory.getDaoUsuario();
        if(daoUsuario.isLoginCadastrado(this.entity.getUsuario().getLogin())) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Inválido",  "O login informado já está sendo utilizado!"));
            return "index.jsf";
        }
        iDaoFornecedor dao = (iDaoFornecedor) getDao();
        if(dao.isNomeCadastrado(entity.getNome())) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome Inválido",  "O fornecedor informado já está cadastrado!"));
            return "index.jsf";
        }

        entity.getUsuario().setLogin(entity.getUsuario().getLogin().trim());
        entity.getUsuario().setSenha(Md5Utils.toMd5(entity.getUsuario().getSenha()));
        entity.getUsuario().setTipo(br.udesc.ceavi.caixeiro.model.TipoUsuario.FORNECEDOR.getTipo());
        daoUsuario.insert(this.entity.getUsuario());
        dao.insert(entity);

        this.entity = getDao().getNewEntity();
        SessionUtils.setParam("user", entity.getUsuario());
        return "home.jsf";
    }

    @Override
    public void save() {
        iDaoUsuario daoUsuario = JDBCFactory.getDaoUsuario();
        entity.getUsuario().setLogin(entity.getUsuario().getLogin().trim());
        entity.getUsuario().setSenha(Md5Utils.toMd5(entity.getUsuario().getSenha()));
        entity.getUsuario().setTipo(br.udesc.ceavi.caixeiro.model.TipoUsuario.FORNECEDOR.getTipo());
        daoUsuario.insert(this.entity.getUsuario());
        super.save(); //To change body of generated methods, choose Tools | Templates.
    }

}