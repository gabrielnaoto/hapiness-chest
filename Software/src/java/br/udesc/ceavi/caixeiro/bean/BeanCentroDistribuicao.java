package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.CentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.Cliente;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import br.udesc.ceavi.core.util.DistanceMatrixCalculator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Samuel Felício Adriano
 */
@RequestScoped
@ManagedBean
public class BeanCentroDistribuicao extends BeanEntity<CentroDistribuicao> {

    protected MapModel geoModel  = new DefaultMapModel();
    protected double   latitude  = Endereco.LATITUDE_UDESC,
                       longitude = Endereco.LONGITUDE_UDESC;


    @Override
    protected Persistible<CentroDistribuicao> getDao() {
        return br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory.getDaoCentroDistribuicao();
    }

    public DataModel<CentroDistribuicao> getListaCentroDistribuicao() {
        return this.getDataModelList();
    }

    public String getCentroMapa() {
        if(!geoModel.getMarkers().isEmpty()) {
            LatLng latlng = geoModel.getMarkers().get(0).getLatlng();
            return latlng.getLat() + ", " + latlng.getLng();
        }

        return Endereco.LATITUDE_UDESC + ", " + Endereco.LONGITUDE_UDESC;
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();

        geoModel.getMarkers().clear();
        geoModel.addOverlay(new Marker(latlng, "Endereço"));

        latitude  = latlng.getLat();
        longitude = latlng.getLng();

        DistanceMatrixCalculator calc = new DistanceMatrixCalculator();
        calc.setOrigin(latitude, longitude);
        calc.addDestination(latitude, longitude);
        entity.getEndereco().setDescricao(calc.getOriginAddress());
    }

    @Override
    public void change() {
        super.change();
        openDialog("dlg");
    }

    @Override
    public void save() {
        this.entity.getEndereco().setLatitude(latitude);
        this.entity.getEndereco().setLongitude(longitude);

        iDaoEndereco daoEndereco = JDBCFactory.getDaoEndereco();
        daoEndereco.insert(entity.getEndereco());

        beforeSave();
        super.save();
        JDBCFactory.getDaoEndereco().limpaEnderecos();

        latitude  = Endereco.LATITUDE_UDESC;
        longitude = Endereco.LONGITUDE_UDESC;
        geoModel.addOverlay(new Marker(new LatLng(latitude, longitude), "Endereço"));
    }

    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
        geoModel.getMarkers().clear();

        if(results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                LatLng        latlng = result.getLatLng();

                this.entity.getEndereco().setLatitude(latlng.getLat());
                this.entity.getEndereco().setLongitude(latlng.getLng());

                geoModel.addOverlay(new Marker(latlng, "Endereço"));

                latitude = latlng.getLat();
                longitude = latlng.getLng();
                this.entity.getEndereco().setDescricao(result.getAddress());
            }
        }
    }

    private void beforeSave() {
        DistanceMatrixCalculator calc = new DistanceMatrixCalculator();
        calc.setOrigin(entity.getEndereco().getLatitude(), entity.getEndereco().getLongitude());

        List<Endereco> registros = new ArrayList();
        for (Endereco endereco : JDBCFactory.getDaoEndereco().getAll()) {
            if (endereco.getId() != entity.getEndereco().getId()) {
                calc.addDestination(endereco.getLatitude(), endereco.getLongitude());
                registros.add(endereco);
            }
        }

        Iterator<String> it = calc.getResult().iterator();
        for (Endereco registro : registros) {
            RelacionamentoEndereco r = new RelacionamentoEndereco();
            r.setEnderecoSaida(entity.getEndereco());
            r.setEnderecoChegada(registro);
            r.setTempo(it.next());

            if (!JDBCFactory.getDaoRelacionamentoEndereco().exists(r)) {
                JDBCFactory.getDaoRelacionamentoEndereco().insert(r);
            }
        }
    }

}