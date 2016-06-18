package br.udesc.ceavi.core.model.dao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel Felício Adriano
 */
public class Relationships<Entity extends br.udesc.ceavi.core.model.Entity> {

    private List<Relation> relations = new java.util.ArrayList<>();


    public void addRelation(boolean key, boolean sequential, String columnName, String modelName, String dbType, Class type) {
        relations.add(new Relation(key, sequential, columnName, modelName, dbType, type));
    }

    public List<Relation> getAllRelations() {
        return relations;
    }

    public List<String> getAllColumnsNames() {
        List<String> columns = new ArrayList<>();

        relations.stream().forEach((relation) -> {
            columns.add(relation.getColumnName());
        });

        return columns;
    }

    public List<String> getAllNonSequentialsColumnsNames() {
        List<String> columns = new ArrayList<>();

        relations.stream().filter((relation) -> (!relation.isSequential())).forEach((relation) -> {
            columns.add(relation.getColumnName());
        });

        return columns;
    }

    public List<String> getAllKeyColumnsNames() {
        List<String> columns = new ArrayList<>();

        relations.stream().filter((relation) -> (relation.isKey())).forEach((relation) -> {
            columns.add(relation.getColumnName());
        });

        return columns;
    }

    public List<String> getAllPreparedParameters() {
        List<String> parameters = new ArrayList<>();

        relations.stream().forEach((item) -> {parameters.add("?");});

        return parameters;
    }

    public List<String> getAllNonSequentialsPreparedParameters() {
        List<String> parameters = new ArrayList<>();

        relations.stream().forEach((item) -> {
            if(!item.isSequential()) {
                parameters.add("?");
            }
        });

        return parameters;
    }

    public List<Class> getAllTypes() {
        List<Class> types = new ArrayList<>();

        relations.stream().forEach((item) -> {
            types.add(item.getType());
        });

        return types;
    }

    public List<Class> getAllNonSequentialsTypes() {
        List<Class> types = new ArrayList<>();

        relations.stream().forEach((item) -> {
            if(!item.isSequential()) {
                types.add(item.getType());
            }
        });

        return types;
    }

    public List<Class> getAllKeyTypes() {
        List<Class> types = new ArrayList<>();

        relations.stream().forEach((item) -> {
            if(item.isKey()) {
                types.add(item.getType());
            }
        });

        return types;
    }

    public List<String> getAllModelNames() {
        List<String> types = new ArrayList<>();

        relations.stream().forEach((item) -> {
            types.add(item.getModelName());
        });

        return types;
    }

    public List<String> getAllNonSequentialsModelNames() {
        List<String> types = new ArrayList<>();

        relations.stream().forEach((item) -> {
            if(!item.isSequential()) {
                types.add(item.getModelName());
            }
        });

        return types;
    }

}