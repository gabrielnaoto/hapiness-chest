package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.caixeiro.model.dao.iDaoUsuario;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.util.encryption.Md5Utils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Samuel Felício Adriano
 */
@SessionScoped
@ManagedBean
public class BeanLogin {

    private String login = "",
            password = "";

    public String login() {
        iDaoUsuario dao = JDBCFactory.getDaoUsuario();
        Usuario user = dao.getUsuario(login, password);

        if (user != null) {
            SessionUtils.setParam("user", user);
            return "home.jsf";
        }

        return null;
    }

    public String logout() {
        SessionUtils.invalidate();
        return "index.jsf";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login.toLowerCase().trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Md5Utils.toMd5(password);
    }

}
