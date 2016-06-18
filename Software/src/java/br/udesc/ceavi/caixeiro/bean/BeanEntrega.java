package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.CentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.Cesta;
import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.Veiculo;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ricardo Augusto Küstner
 */
@RequestScoped
@ManagedBean
public class BeanEntrega extends BeanEntity<Entrega> {


    /**
     * Garanti minha vaga no inferno dos programadores fazendo isso
     * Se + alguém usar isso dá uma passada lá na área VIP qnd chegar, vo tá lá fazendo um churrascão
     */
    protected static Veiculo            veiculo;
    protected static CentroDistribuicao centroDistribuicao;
    protected static Cesta              cesta;


    @Override
    protected Persistible<Entrega> getDao() {
        return JDBCFactory.getDaoEntrega();
    }

    public DataModel<Entrega> getListaEntrega() {
        return this.getDataModelList();
    }

    public List<Veiculo> listaVeiculo(String query) {
        List<Veiculo> results = new ArrayList<>();

        Iterable<Veiculo> it = JDBCFactory.getDaoVeiculo().getAll();

        for (Veiculo veiculo : it) {
            results.add(veiculo);
        }

        return results;
    }

    public void onSelectVeiculo(SelectEvent event) {
        veiculo = (Veiculo) event.getObject();
    }

    public List<CentroDistribuicao> listaCentroDistribuicao(String query) {
        List<CentroDistribuicao>     results  = new ArrayList<>();
        Iterable<CentroDistribuicao> iterable = JDBCFactory.getDaoCentroDistribuicao().getAll();

        for(CentroDistribuicao centro : iterable) {
            results.add(centro);
        }

        return results;
    }

    public void onSelectCentroDistribuicao(SelectEvent event) {
        centroDistribuicao = (CentroDistribuicao) event.getObject();
    }

    public void onSelectCesta(SelectEvent event) {
        cesta = (Cesta) event.getObject();
    }

    @Override
    public void insert() {
        veiculo            = new Veiculo();
        centroDistribuicao = new CentroDistribuicao();
        this.entity.setData("15/06/2016");
//        this.cesta = new Cesta();
        super.insert();
    }


    @Override
    public void save() {
        // Se uma coisa parece idiota, mas ela funciona, então ela não é idiota.
        this.entity.setVeiculo(veiculo);
        this.entity.setCentroDistribuicao(centroDistribuicao);

        this.entity.setData("15/06/2016");
        super.save();

        veiculo            = new Veiculo();
        centroDistribuicao = new CentroDistribuicao();
//        this.cesta = new Cesta();
    }

}