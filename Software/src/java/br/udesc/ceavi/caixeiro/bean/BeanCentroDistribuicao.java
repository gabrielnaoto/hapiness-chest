package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.CentroDistribuicao;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
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
    }

    @Override
    public void save() {
        this.entity.getEndereco().setLatitude(latitude);
        this.entity.getEndereco().setLongitude(longitude);

        iDaoEndereco daoEndereco = JDBCFactory.getDaoEndereco();
        daoEndereco.insert(entity.getEndereco());

        super.save();

        latitude  = Endereco.LATITUDE_UDESC;
        longitude = Endereco.LONGITUDE_UDESC;
    }

}