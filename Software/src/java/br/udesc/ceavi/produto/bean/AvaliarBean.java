package br.udesc.ceavi.produto.bean;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.produto.model.entidade.Produto;
import br.udesc.ceavi.produto.uc.AvaliarProdutoUC;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class AvaliarBean {

    private List<Produto> produtos;
    private Produto produtoSelecionado;
    private AvaliarProdutoUC apuc;

    @PostConstruct
    public void init() {
        apuc = new AvaliarProdutoUC();
        produtos = apuc.obterProdutos(((Usuario) SessionUtils.getParam("user")).getId());
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public void onrate() {
        if (apuc.avaliar(produtoSelecionado, ((Usuario) SessionUtils.getParam("user")), produtoSelecionado.getSatisfacao())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sua nota foi registrada.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve um problema, tente novamente mais tarde.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void oncancel() {
        if (apuc.avaliar(produtoSelecionado, ((Usuario) SessionUtils.getParam("user")), 0)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Você apagou sua avaliação.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao apagar avaliação");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

}
