/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.bean;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.model.entidade.Produto;
import br.udesc.ceavi.produto.uc.OferecerProdutoUC;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ignoi
 */
@ManagedBean
@SessionScoped
public class OferecerBean {

    private List<Produto> produtos;
    private OferecerProdutoUC opuc;
    private List<Categoria> categoriasAtuais;
    private Produto cadastrando;
    private Usuario usuario;
    private Categoria cate;

    @PostConstruct
    public void init() {
        usuario = ((Usuario) SessionUtils.getParam("user"));
        opuc = new OferecerProdutoUC();
        produtos = opuc.obterProdutos(usuario);
        categoriasAtuais = opuc.obterCategorias();
        cadastrando = new Produto();
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
        opuc.oferecer(cadastrando, usuario, cadastrando.getValor());
        produtos = opuc.obterProdutos(usuario);
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

}
