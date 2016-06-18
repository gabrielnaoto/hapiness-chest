/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 5105011424
 */
@SessionScoped
@ManagedBean
public class LoginBean {

    private String usuario = "";
    private String senha = "";

    public String logar() {

        if (usuario.equals("admin") && senha.equals("admin")) {
            SessionUtil.setParam("usuario", usuario);
            return "home.jsf";
        } else {
            return null;
        }

    }

    public String logout() {
        SessionUtil.invalidate();
        return "index.jsf";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
