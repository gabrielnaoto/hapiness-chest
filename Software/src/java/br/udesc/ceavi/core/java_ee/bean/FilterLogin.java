package br.udesc.ceavi.core.java_ee.bean;

import br.udesc.ceavi.caixeiro.model.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel Felício Adriano
 */
@WebFilter(servletNames = {"Faces Servlet"})
public class FilterLogin implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requisition = (HttpServletRequest) request;
        HttpSession        session     = requisition.getSession();
        Usuario            user        = (Usuario) session.getAttribute("user");

        boolean userLogged              = user != null,
                requestEndsWithIndex    = requisition.getRequestURI().endsWith("index.jsf"),
                requestContainsResource = requisition.getRequestURI().contains("javax.faces.resource/");

        if(userLogged || requestEndsWithIndex || requestContainsResource) {
            if((session.getAttribute("usuario") != null) && (requisition.getRequestURI().endsWith("index.jsf"))){
                redirect("home.jsf", response);
            }
            chain.doFilter(request, response);
        } else {
            redirect("index.jsf", response);
        }
    }

    @Override
    public void destroy() {}

    private void redirect(String url, ServletResponse response) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.sendRedirect(url);
    }

}
