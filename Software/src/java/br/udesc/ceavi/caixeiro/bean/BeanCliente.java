package br.udesc.ceavi.caixeiro.bean;

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
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Ricardo Augusto Küstner
 */
@SessionScoped
@ManagedBean
public class BeanCliente  extends BeanEntity<Cliente> {

    protected MapModel geoModel  = new DefaultMapModel();
    protected double   latitude  = Endereco.LATITUDE_UDESC,
                       longitude = Endereco.LONGITUDE_UDESC;

    @Override
    protected Persistible<Cliente> getDao() {
        return br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory.getDaoCliente();
    }

    public DataModel<Cliente> getListaCliente() {
        return this.getDataModelList();
    }

    public String getCentroMapa() {
        if (entity != null && entity.getEndereco().getLatitude() != 0) {
            return entity.getEndereco().getLatitude() + ", " + entity.getEndereco().getLongitude();
        }
        else if (!geoModel.getMarkers().isEmpty()) {
            LatLng latlng = geoModel.getMarkers().get(0).getLatlng();
            return latlng.getLat() + ", " + latlng.getLng();
        }

        return Endereco.LATITUDE_UDESC + ", " + Endereco.LONGITUDE_UDESC;
    }

    @Override
    public void change() {
        super.change();
        JDBCFactory.getDaoEndereco().persists(entity.getEndereco());

        LatLng latlng = new LatLng(entity.getEndereco().getLatitude(), entity.getEndereco().getLongitude());
        geoModel.getMarkers().clear();
        geoModel.addOverlay(new Marker(latlng, "Endereço"));
    }



    public MapModel getGeoModel() {
        return geoModel;
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();

        geoModel.getMarkers().clear();
        geoModel.addOverlay(new Marker(latlng, "Endereço"));

        entity.getEndereco().setLatitude(latlng.getLat());
        entity.getEndereco().setLongitude(latlng.getLng());

        //addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Parabéns", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));

        latitude  = latlng.getLat();
        longitude = latlng.getLng();
    }

    @Override
    public void save() {
        this.entity.getEndereco().setLatitude(latitude);
        this.entity.getEndereco().setLongitude(longitude);

        iDaoEndereco daoEndereco = JDBCFactory.getDaoEndereco();

        if(!daoEndereco.exists(entity.getEndereco())) {
            daoEndereco.insert(entity.getEndereco());
        } else {
            daoEndereco.update(entity.getEndereco());
        }

        beforeSave();
        super.save();

        latitude  = Endereco.LATITUDE_UDESC;
        longitude = Endereco.LONGITUDE_UDESC;
    }

    private void beforeSave() {
        DistanceMatrixCalculator calc = new DistanceMatrixCalculator();
        calc.setOrigin(entity.getEndereco().getLatitude(), entity.getEndereco().getLongitude());

        List<Endereco> registros = new ArrayList();
        for (Cliente cliente : getDao().getAll()) {
            JDBCFactory.getDaoEndereco().persists(cliente.getEndereco());
            if (cliente.getId() != entity.getEndereco().getId()) {
                calc.addDestination(cliente.getEndereco().getLatitude(), cliente.getEndereco().getLongitude());
                registros.add(cliente.getEndereco());
                //break;
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