/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.converter;

import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.PersistenceType;
import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.entidade.SampleEntity;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("categoriac")
public class CategoriaConverter implements Converter, Serializable {

    private final CategoriaDAO cdao = Persistence.getPersistence(PersistenceType.JDBC).getCategoriaDAO();

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return cdao.pesquisar(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
        if (value != null && !"".equals(value)) {
            SampleEntity entity = (SampleEntity) value;
            Integer codigo = entity.getCodigo();
            if (codigo != null) {
                return cdao.pesquisar(codigo).getDescricao();
            }
        }
        return (String) value;
    }

}
