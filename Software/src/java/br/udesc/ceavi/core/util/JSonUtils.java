package br.udesc.ceavi.core.util;

import java.util.ArrayList;

/**
 *
 * @author Samuel Felício Adriano
 * @since  04/06/2016
 */
public class JSonUtils {

    public static final String toJson(Object object) {
        StringBuilder json               = new StringBuilder("{");
        java.util.List<String> atributes = new ArrayList<>();

        Object original = object;
        for(java.lang.reflect.Field field : original.getClass().getDeclaredFields()) {
            if(field.getModifiers() == java.lang.reflect.Modifier.PUBLIC) {
                atributes.add("\"" + field.getName() + "\": " + BeanUtils.callGetter(object, field.getName()));
            }
            try {
                String methodName               = "get" + StringUtils.toCamelCase(field.getName(), true);
                java.lang.reflect.Method method = object.getClass().getMethod(methodName);
                if(MethodUtils.isGetter(method)) {
                    atributes.add("\"" + field.getName() + "\": " + BeanUtils.callGetter(object, field.getName()));
                }
            } catch(NoSuchMethodException exception) {
                continue;
            }
        }

        String[] values = atributes.toArray(new String[atributes.size()]);
        return json.append(String.join(", ", values)).
                    append("}").
                    toString();
    }

}