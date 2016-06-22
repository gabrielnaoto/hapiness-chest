/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.bean;

import br.udesc.ceavi.caixeiro.model.Cesta;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ricardo Augusto Küstner
 */
@FacesConverter("conversorCesta")
public class ConversorCesta implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                Cesta v = new Cesta();
                v.setId(Integer.parseInt(value));
                JDBCFactory.getDaoCesta().persists(v);
                return v;
            } catch(NumberFormatException e) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Cesta) object).getId());
        }
        else {
            return null;
        }
    }

}
