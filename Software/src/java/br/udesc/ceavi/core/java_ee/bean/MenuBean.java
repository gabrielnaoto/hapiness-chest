/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and op9en the template in the editor.
 */
package br.udesc.ceavi.core.java_ee.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 * Cria os itens do menu
 * @author Ricardo Augusto Küstner
 */
@SessionScoped
@ManagedBean(name="menuBean")
public class MenuBean {

    private MenuModel menuItens = new DefaultMenuModel();

    public MenuBean() {

        // fazer um switch para os tipos de usuário
        carregaMenuAdmin();
    }

    private void carregaMenuAdmin() {
        DefaultMenuItem veiculo = new DefaultMenuItem("Veículo");
        veiculo.setIcon("ui-icon-key");
        veiculo.setUrl("veiculo.jsf");

        DefaultMenuItem centro = new DefaultMenuItem("Centro Distribuição");
        centro.setIcon("ui-icon-home");
        centro.setUrl("centro_distribuicao.jsf");

        DefaultMenuItem cliente = new DefaultMenuItem("Cliente");
        cliente.setStyleClass("fa fa-fw fa-align-left");
        cliente.setUrl("cliente.jsf");

        DefaultSubMenu cadastros = new DefaultSubMenu("Cadastros");
        cadastros.addElement(veiculo);
        cadastros.addElement(centro);
        cadastros.addElement(cliente);

        DefaultMenuItem editar = new DefaultMenuItem("Editar", "ui-icon-close", "#");
        DefaultMenuItem sair = new DefaultMenuItem("Sair", "ui-icon-close", "index.jsf");
        sair.setAjax(false);
        sair.setCommand("#{loginBean.logout}");

        DefaultSubMenu conta = new DefaultSubMenu("Conta");
        conta.addElement(editar);
        conta.addElement(sair);

        DefaultMenuItem manterUsuarios = new DefaultMenuItem("Manter usuários", "ui-icon-contact", "#");
        DefaultMenuItem avaliarProdutos = new DefaultMenuItem("Avaliar produtos", "ui-icon-tag", "#");
        DefaultMenuItem oferecerProdutos = new DefaultMenuItem("Oferecer produtos", "ui-icon-plusthick", "#");
        DefaultMenuItem criarCesta = new DefaultMenuItem("Criar cesta", "ui-icon-pencil", "#");
        DefaultMenuItem montarEntrega = new DefaultMenuItem("Montar entrega", "ui-icon-suitcase", "entrega.jsf");

        this.menuItens.addElement(conta);
        this.menuItens.addElement(cadastros );
        this.menuItens.addElement(manterUsuarios);
        this.menuItens.addElement(avaliarProdutos);
        this.menuItens.addElement(oferecerProdutos);
        this.menuItens.addElement(criarCesta);
        this.menuItens.addElement(montarEntrega);
    }

    public MenuModel getMenuItens() {
        return menuItens;
    }

    public void setMenuItens(MenuModel menuItens) {
        this.menuItens = menuItens;
    }

    public String ajaxAction(){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajax Update"));
        return "";
    }
}
