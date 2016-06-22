/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.converter;

import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.PersistenceType;
import br.udesc.ceavi.produto.model.dao.categoria.CategoriaDAO;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.model.entidade.SampleEntity;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoriaConverter", forClass = Categoria.class)
public class CategoriaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
         if (!value.equals("0")) {
            return (Categoria) Persistence.getPersistence(PersistenceType.JDBC).getCategoriaDAO().pesquisar(Integer.parseInt(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
          Categoria p;

        try {
            p = (Categoria) value;
        } catch (Exception e) {
           p = new Categoria();
        }

        return String.valueOf(p.getId());
    }

}
