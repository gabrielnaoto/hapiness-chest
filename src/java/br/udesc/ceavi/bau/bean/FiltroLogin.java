/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.bean;

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
 * @author ignoi
 */
@WebFilter(servletNames = {"Faces Servlet"})
public class FiltroLogin implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest requisicao = (HttpServletRequest) request;
        HttpSession session = requisicao.getSession();
        if ((session.getAttribute("usuario") != null)
                || (requisicao.getRequestURI().endsWith("index.jsf"))
                || (requisicao.getRequestURI().contains("javax.faces.resource/"))) {
            if ((session.getAttribute("usuario") != null) && (requisicao.getRequestURI().endsWith("index.jsf"))){
                redireciona("home.jsf", response);
            }
            chain.doFilter(request, response);
        } else {
            redireciona("index.jsf", response);
        }
    }

    private void redireciona(String url, ServletResponse response) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.sendRedirect(url);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
