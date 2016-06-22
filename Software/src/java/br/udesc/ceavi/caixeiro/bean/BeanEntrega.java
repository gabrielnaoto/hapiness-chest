package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.CentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.Cesta;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.Veiculo;
import br.udesc.ceavi.caixeiro.util.AlgoritmoACO;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author Ricardo Augusto Küstner
 */
@SessionScoped
@ManagedBean
public class BeanEntrega extends BeanEntity<Entrega> {

    protected MapModel modelMapa  = new DefaultMapModel();

    @Override
    public Entrega getEntity() {
        if (entity == null) {
            entity = new Entrega();
        }
        return super.getEntity(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    protected Persistible<Entrega> getDao() {
        return JDBCFactory.getDaoEntrega();
    }

    public DataModel<Entrega> getListaEntrega() {
        return this.getDataModelList();
    }

    public String getCentroMapa() {
        if (entity != null) {
            getDao().persists(entity);
            JDBCFactory.getDaoCentroDistribuicao().persists(entity.getCentroDistribuicao());
            JDBCFactory.getDaoEndereco().persists(entity.getCentroDistribuicao().getEndereco());

            return entity.getCentroDistribuicao().getEndereco().getLatitude() + ", " + entity.getCentroDistribuicao().getEndereco().getLongitude();
        }
        return null;
    }

    public MapModel getModelMapa() {
        if (entity != null && entity.getId() > 0) {
            String trajeto = JDBCFactory.getDaoEntrega().getTrajeto(entity);
            Endereco e = new Endereco();
            LatLng posicao;

            modelMapa.getMarkers().clear();
            modelMapa.getPolylines().clear();

            Polyline polyline = new Polyline();
            polyline.setStrokeWeight(3);
            polyline.setStrokeColor("#FF0000");
            polyline.setStrokeOpacity(0.7);
            modelMapa.addOverlay(polyline);

            int i = 1;
            String[] lista = trajeto.trim().split("\\s");
            for (String id : lista) {
                e.setId(Integer.parseInt(id));
                JDBCFactory.getDaoEndereco().persists(e);

                posicao = new LatLng(e.getLatitude(), e.getLongitude());

                polyline.getPaths().add(posicao);


                if (i == 1) {
                    modelMapa.addOverlay(new Marker(posicao, "#" + i++ + " " + e.getDescricao(), "Nada aqui!", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
                } else if (i != lista.length) {
                    modelMapa.addOverlay(new Marker(posicao, "#" + i++ + " " + e.getDescricao()));
                }
            }

            entity = null;
        }
        return modelMapa;
    }

    public void mostraMapa() {
        entity = dataModel.getRowData();
        openDialog("dlgMapa");
    }

    public List<Veiculo> listaVeiculo(String query) {
        List<Veiculo> results = new ArrayList<>();

        Iterable<Veiculo> it = JDBCFactory.getDaoVeiculo().getAll();

        for (Veiculo veiculo : it) {
            if (veiculo.getDescricao().toLowerCase().contains(query.toLowerCase())) {
                results.add(veiculo);
            }
        }

        return results;
    }

    public List<CentroDistribuicao> listaCentroDistribuicao(String query) {
        List<CentroDistribuicao>     results  = new ArrayList<>();
        Iterable<CentroDistribuicao> iterable = JDBCFactory.getDaoCentroDistribuicao().getAll();

        for(CentroDistribuicao centro : iterable) {
            results.add(centro);
        }

        return results;
    }

    public List<Cesta> listaCesta(String query) {
        List<Cesta> results = new ArrayList<>();

        Iterable<Cesta> it = JDBCFactory.getDaoCesta().getAll();

        for (Cesta cesta : it) {
            results.add(cesta);
        }

        // chumbamento avançado
        if (results.isEmpty()) {
            Cesta c = new Cesta();
            c.setTrimestre(2);
            c.setAno(2016);
            c.setPeso(2.5f);

            JDBCFactory.getDaoCesta().insert(c);
            results.add(c);
        }

        return results;
    }

    @Override
    public void save() {
        entity.setData(new Date());
        super.save();
        closeDialog("dlg");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cálculo efetuado com sucesso!"));
    }

    @Override
    protected void afterInsert() {
        JDBCFactory.getDaoEntrega().separaPagamentos(entity);
        calculaACO();
    }

    @Override
    protected void afterUpdate() {
        JDBCFactory.getDaoEntrega().separaPagamentos(entity);
        calculaACO();
    }

    @Override
    public void change() {
        super.change();

        JDBCFactory.getDaoCesta().persists(entity.getCesta());
        JDBCFactory.getDaoCentroDistribuicao().persists(entity.getCentroDistribuicao());
        JDBCFactory.getDaoVeiculo().persists(entity.getVeiculo());

        openDialog("dlg");
    }

    private void calculaACO() {
        AlgoritmoACO aco = new AlgoritmoACO();
        aco.calcula(entity);
    }



}