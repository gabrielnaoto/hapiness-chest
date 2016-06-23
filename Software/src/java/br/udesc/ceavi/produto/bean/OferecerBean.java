package br.udesc.ceavi.produto.bean;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.model.entidade.Produto;
import br.udesc.ceavi.produto.uc.OferecerProdutoUC;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class OferecerBean {

    private List<Produto> produtos;
    private OferecerProdutoUC opuc;
    private List<Categoria> categoriasAtuais;
    private Produto cadastrando;
    private Usuario usuario;
    private Categoria cate;

    @PostConstruct
    public void init() {
        opuc = new OferecerProdutoUC();
        cadastrando = new Produto();
        atualizar();
    }

    private UIOutput label;

    public UIOutput getLabel() {
        return this.label;
    }

    public void setLabel(UIOutput label) {
        this.label = label;
    }
    
    private UIComponent select;

    public UIComponent getSelect() {
        return this.select;
    }

    public void setSelect(UIComponent select) {
        this.select = select;
    }

    private UIComponent form;

    public UIComponent getForm() {
        return this.form;
    }

    public void setForm(UIComponent form) {
        this.form = form;
    }

    public Categoria getCate() {
        return cate;
    }

    public void setCate(Categoria cate) {
        this.cate = cate;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public OferecerProdutoUC getOpuc() {
        return opuc;
    }

    public void setOpuc(OferecerProdutoUC opuc) {
        this.opuc = opuc;
    }

    public Produto getCadastrando() {
        return cadastrando;
    }

    public void setCadastrando(Produto cadastrando) {
        this.cadastrando = cadastrando;
    }

    public void salvar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (opuc.oferecer(cadastrando, usuario, cadastrando.getValor())) {
            produtos = opuc.obterProdutos(usuario);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Produto adicionado com sucesso!"));
        } else {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve um problema ao adicionar."));
        }
        cadastrando = new Produto();
    }

    public void cancelar() {
        cadastrando = new Produto();
    }

    public List<Categoria> getCategoriasAtuais() {
        return categoriasAtuais;
    }

    public void setCategoriasAtuais(List<Categoria> categoriasAtuais) {
        this.categoriasAtuais = categoriasAtuais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void atualizar() {
        usuario = ((Usuario) SessionUtils.getParam("user"));
        produtos = opuc.obterProdutos(usuario);
        categoriasAtuais = opuc.obterCategorias();
    }

}
