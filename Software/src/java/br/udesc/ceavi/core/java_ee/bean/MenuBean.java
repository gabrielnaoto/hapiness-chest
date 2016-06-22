package br.udesc.ceavi.core.java_ee.bean;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.caixeiro.model.dao.iDaoFornecedor;
import br.udesc.ceavi.caixeiro.model.dao.iDaoUsuario;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.PersistenceType;
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
 *
 * @author Ricardo Augusto Küstner
 */
@SessionScoped
@ManagedBean(name = "menuBean")
public class MenuBean {

    private MenuModel menuItens = new DefaultMenuModel();

    public MenuBean() {
        Usuario usuario = (Usuario) SessionUtils.getParam("user");

        switch (usuario.getTipo()) {
            case 1:
                carregaMenuAdmin();
                break;
            case 2:
                carregaMenuFornecedor();
                break;
            case 3:
                carregaMenuCliente();
                break;
            default:
                carregaMenuDefault();
        }
    }

    private void carregaMenuAdmin() {
        DefaultMenuItem veiculo = new DefaultMenuItem("Veículo");
        veiculo.setIcon("ui-icon-key");
        veiculo.setUrl("veiculo.jsf");

        DefaultMenuItem centro = new DefaultMenuItem("Centro Distribuição");
        centro.setIcon("ui-icon-home");
        centro.setUrl("centro_distribuicao.jsf");

        DefaultMenuItem cliente = new DefaultMenuItem("Cliente");
        cliente.setIcon("ui-icon-contact");
        cliente.setUrl("cliente.jsf");

        DefaultMenuItem fornecedor = new DefaultMenuItem("Fornecedor");
        fornecedor.setIcon("ui-icon-clipboard");
        fornecedor.setUrl("fornecedor.jsf");

        DefaultSubMenu cadastros = new DefaultSubMenu("Cadastros");
        cadastros.addElement(veiculo);
        cadastros.addElement(centro);
        cadastros.addElement(cliente);
        cadastros.addElement(fornecedor);

        DefaultMenuItem editar = new DefaultMenuItem("Editar", "ui-icon-close", "#");
        DefaultMenuItem sair = new DefaultMenuItem("Sair", "ui-icon-close");
        sair.setAjax(false);
        sair.setCommand("#{beanLogin.logout()}");

        DefaultSubMenu conta = new DefaultSubMenu("Conta");
        conta.addElement(editar);
        conta.addElement(sair);

        DefaultMenuItem avaliarProdutos = new DefaultMenuItem("Avaliar produtos", "ui-icon-tag", "avaliar.jsf");
        DefaultMenuItem oferecerProdutos = new DefaultMenuItem("Oferecer produtos", "ui-icon-plusthick", "oferecer.jsf");
        DefaultMenuItem criarCesta = new DefaultMenuItem("Gerenciar cesta", "ui-icon-pencil", "cesta.jsf");
        DefaultMenuItem montarEntrega = new DefaultMenuItem("Montar entrega", "ui-icon-suitcase", "entrega.jsf");

        this.menuItens.addElement(conta);
        this.menuItens.addElement(cadastros);
        this.menuItens.addElement(avaliarProdutos);
        this.menuItens.addElement(oferecerProdutos);
        this.menuItens.addElement(criarCesta);
        this.menuItens.addElement(montarEntrega);
    }

    private void carregaMenuFornecedor() {
        DefaultMenuItem editar = new DefaultMenuItem("Editar", "ui-icon-close", "#");
        DefaultMenuItem sair = new DefaultMenuItem("Sair", "ui-icon-close", "index.jsf");
        sair.setAjax(false);
        sair.setCommand("#{beanLogin.logout}");

        DefaultSubMenu conta = new DefaultSubMenu("Conta");
        conta.addElement(editar);
        conta.addElement(sair);

        DefaultMenuItem avaliarProdutos = new DefaultMenuItem("Avaliar produtos", "ui-icon-tag", "avaliar.jsf");
        DefaultMenuItem oferecerProdutos = new DefaultMenuItem("Oferecer produtos", "ui-icon-plusthick", "oferecer.jsf");

        this.menuItens.addElement(conta);
        this.menuItens.addElement(avaliarProdutos);
        this.menuItens.addElement(oferecerProdutos);
    }

    private void carregaMenuCliente() {
        DefaultMenuItem editar = new DefaultMenuItem("Editar", "ui-icon-close", "#");
        DefaultMenuItem sair = new DefaultMenuItem("Sair", "ui-icon-close", "index.jsf");
        sair.setAjax(false);
        sair.setCommand("#{beanLogin.logout}");

        DefaultSubMenu conta = new DefaultSubMenu("Conta");
        conta.addElement(editar);
        conta.addElement(sair);
        DefaultMenuItem avaliarProdutos = new DefaultMenuItem("Avaliar produtos", "ui-icon-tag", "avaliar.jsf");

        this.menuItens.addElement(conta);
        this.menuItens.addElement(avaliarProdutos);
    }

    private void carregaMenuDefault() {
        DefaultMenuItem editar = new DefaultMenuItem("Editar", "ui-icon-close", "#");
        DefaultMenuItem sair = new DefaultMenuItem("Sair", "ui-icon-close", "index.jsf");
        sair.setAjax(false);
        sair.setCommand("#{beanLogin.logout}");
        DefaultSubMenu conta = new DefaultSubMenu("Conta");
        conta.addElement(editar);
        conta.addElement(sair);
        this.menuItens.addElement(conta);
    }

    public MenuModel getMenuItens() {
        return menuItens;
    }

    public void setMenuItens(MenuModel menuItens) {
        this.menuItens = menuItens;
    }

    public String ajaxAction() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajax Update"));
        return "";
    }

}
