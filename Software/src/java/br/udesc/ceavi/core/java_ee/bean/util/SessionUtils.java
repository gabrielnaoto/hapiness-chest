package br.udesc.ceavi.core.java_ee.bean.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel Felício Adriano
 */
public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static void setParam(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getParam(String key) {
        return getSession().getAttribute(key);
    }

    public static void remove(String key) {
        getSession().removeAttribute(key);
    }

    public static void invalidate() {
        getSession().invalidate();
    }

}