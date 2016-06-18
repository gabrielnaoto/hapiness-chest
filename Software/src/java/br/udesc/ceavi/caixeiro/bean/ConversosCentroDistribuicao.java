package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.CentroDistribuicao;
import br.udesc.ceavi.core.util.StringUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Samuel Felício Adriano
 */
@FacesConverter("conversorCentroDistribuicao")
public class ConversosCentroDistribuicao implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(StringUtils.isEmpty(value)) {
            return null;
        }

        CentroDistribuicao centro = new CentroDistribuicao();
        centro.setId(Integer.parseInt(value));
        return centro;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return null;
        }

        return String.valueOf(((CentroDistribuicao) value).getId());
    }

}