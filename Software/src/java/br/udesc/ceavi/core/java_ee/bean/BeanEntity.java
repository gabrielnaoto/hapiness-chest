package br.udesc.ceavi.core.java_ee.bean;

import br.udesc.ceavi.core.persistence.Persistible;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Samuel Felício Adriano
 */
@SessionScoped
@ManagedBean
public abstract class BeanEntity<Entity extends br.udesc.ceavi.core.model.Entity> {

    protected Entity              entity;
    protected List<Entity>        list;
    protected DataModel<Entity>   dataModel;
    protected Persistible<Entity> dao;


    public BeanEntity() {
        this.entity = this.getDao().getNewEntity();
        this.list   = new ArrayList<>();
        dataModel   = new ListDataModel(list);
    }


    protected abstract Persistible<Entity> getDao();

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    protected Iterable<Entity> getIterableEntity() {
        return getDao().getAll();
    }

    public void save() {
        if(!getDao().exists(entity)) {
            getDao().insert(entity);
            afterEntityInsert();
        } else {
            getDao().update(entity);
        }
        this.entity = null;
    }

    public void change() {
        this.entity = dataModel.getRowData();
    }

    public void insert() {
        this.entity = getDao().getNewEntity();
    }

    public void delete() {
        getDao().delete(dataModel.getRowData());
    }

    public DataModel<Entity> getDataModelList() {
        this.list = new ArrayList<>();
        loadList();
        dataModel = new ListDataModel(list);

        return this.dataModel;
    }

    protected void loadList() {
        for(Entity currentEntity : this.getIterableEntity()) {
            this.list.add(currentEntity);
        }
    }

    protected void afterEntityInsert() {}

}