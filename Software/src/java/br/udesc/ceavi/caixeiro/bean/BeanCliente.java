package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Cliente;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCliente;
import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.caixeiro.model.dao.iDaoUsuario;
import br.udesc.ceavi.core.java_ee.bean.BeanEntity;
import br.udesc.ceavi.core.java_ee.bean.util.SessionUtils;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistible;
import br.udesc.ceavi.core.util.DistanceMatrixCalculator;
import br.udesc.ceavi.core.util.encryption.Md5Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

        latitude  = latlng.getLat();
        longitude = latlng.getLng();

        DistanceMatrixCalculator calc = new DistanceMatrixCalculator();
        calc.setOrigin(latitude, longitude);
        calc.addDestination(latitude, longitude);
        entity.getEndereco().setDescricao(calc.getOriginAddress());
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

    @Override
    public void save() {
        this.entity.getEndereco().setLatitude(latitude);
        this.entity.getEndereco().setLongitude(longitude);

        iDaoUsuario daoUsuario = JDBCFactory.getDaoUsuario();
        entity.getUsuario().setLogin(entity.getUsuario().getLogin().trim());
        entity.getUsuario().setSenha(Md5Utils.toMd5(entity.getUsuario().getSenha()));
        entity.getUsuario().setTipo(br.udesc.ceavi.caixeiro.model.TipoUsuario.CLIENTE.getTipo());
        daoUsuario.insert(this.entity.getUsuario());
        iDaoEndereco daoEndereco = JDBCFactory.getDaoEndereco();

        if(!daoEndereco.exists(entity.getEndereco()) || entity.getEndereco().getId() == 0) {
            daoEndereco.insert(entity.getEndereco());
        } else {
            daoEndereco.update(entity.getEndereco());
        }

        beforeSave();
        super.save();
        JDBCFactory.getDaoEndereco().limpaEnderecos();

        latitude  = Endereco.LATITUDE_UDESC;
        longitude = Endereco.LONGITUDE_UDESC;
        geoModel.addOverlay(new Marker(new LatLng(latitude, longitude), "Endereço"));
    }

    private void beforeSave() {
        DistanceMatrixCalculator calc = new DistanceMatrixCalculator();
        calc.setOrigin(entity.getEndereco().getLatitude(), entity.getEndereco().getLongitude());

        List<Endereco> registros = new ArrayList();
        for (Endereco endereco : JDBCFactory.getDaoEndereco().getAll()) {
            if (endereco.getId() != entity.getEndereco().getId()) {
                calc.addDestination(endereco.getLatitude(), endereco.getLongitude());
                registros.add(endereco);
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

    public String cadastrar() {
        iDaoUsuario daoUsuario = JDBCFactory.getDaoUsuario();
        if(daoUsuario.isLoginCadastrado(this.entity.getUsuario().getLogin())) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Inválido",  "O login informado já está sendo utilizado!"));
            return "index.jsf";
        }

        iDaoCliente daoCliente = (iDaoCliente) getDao();
        if(daoCliente.isNomeCadastrado(entity.getNome())) {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome Inválido",  "O cliente informado já está cadastrado!"));
            return "index.jsf";
        }

        entity.getUsuario().setLogin(entity.getUsuario().getLogin().trim());
        entity.getUsuario().setSenha(Md5Utils.toMd5(entity.getUsuario().getSenha()));
        entity.getUsuario().setTipo(br.udesc.ceavi.caixeiro.model.TipoUsuario.CLIENTE.getTipo());
        daoUsuario.insert(this.entity.getUsuario());

        iDaoEndereco daoEndereco = JDBCFactory.getDaoEndereco();
        this.entity.getEndereco().setLatitude(latitude);
        this.entity.getEndereco().setLongitude(longitude);
        daoEndereco.insert(entity.getEndereco());

        beforeSave();
        super.save();

        this.entity = getDao().getNewEntity();
        SessionUtils.setParam("user", entity.getUsuario());
        return "home.jsf";
    }

}