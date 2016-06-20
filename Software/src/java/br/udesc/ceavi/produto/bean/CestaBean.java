/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.bean;

import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.model.entidade.Cesta;
import br.udesc.ceavi.produto.uc.GerenciarCestaUC;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ignoi
 */
@ManagedBean
@ViewScoped
public class CestaBean {

    private FacesContext facesContext;
    private List<Cesta> cestas;
    private GerenciarCestaUC gcuc;
    private Cesta atual;

    private Cesta cesta;
    private Categoria categoria;

    @PostConstruct
    public void init() {
        facesContext = FacesContext.getCurrentInstance();
        gcuc = new GerenciarCestaUC();
        atualizar();
        cesta = new Cesta();
        categoria = new Categoria();
    }

    public void onDateSelect(SelectEvent event) {
        facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }

    public List<Cesta> getCestas() {
        return cestas;
    }

    public void setCestas(List<Cesta> cestas) {
        this.cestas = cestas;
    }

    public Cesta getCesta() {
        return cesta;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Cesta getAtual() {
        return atual;
    }

    public void setAtual(Cesta atual) {
        this.atual = atual;
    }

    public void salvar() {
        facesContext = FacesContext.getCurrentInstance();
        if (gcuc.criar(cesta)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cesta adicionada com sucesso!"));
            atualizar();
            cesta = new Cesta();
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve um problema ao adicionar a cesta."));
        }
    }

    public boolean podeFinalizarCesta() {
        boolean mckp = true; //verificar se há compra realizada
        boolean tsp = true; //verificar se todas as entregas foram feitas
        return mckp && tsp;
    }

    public void cancelar() {
        cesta = new Cesta();
        categoria = new Categoria();
    }

    public void adicionar() {
        cesta.addCategoria(categoria);
        categoria = new Categoria();
    }

    public void atualizar() {
        cestas = gcuc.obterCestas();
        atual = gcuc.getAtual();
    }

    public String getData(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public String getValor(double v) {
        DecimalFormat df = new DecimalFormat("R$ ###,###.00");
        return df.format(v);
    }

}
