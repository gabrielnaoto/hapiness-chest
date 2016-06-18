package br.udesc.ceavi.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for Methods
 *
 * @author Samuel Felício Adriano
 * @since  27/05/2016
 */
public class MethodUtils {

    public static Method[] getMethodsFromClass(Class aClass) {
        return aClass.getMethods();
    }


    public static Method[] getMethodsFromClassByName(Class aClass, String methodName) {
        List<Method> methods = new ArrayList<>();

        for(Method method : aClass.getMethods()) {
            if(method.getName().equalsIgnoreCase(methodName)) {
                methods.add(method);
            }
        }

        return methods.toArray(null);
    }

    public static boolean isGetter(Method aMethod) {
        boolean startsWithGet         = aMethod.getName().startsWith("get"),
                returnsVoid           = aMethod.getReturnType() == void.class,
                parametersLenghIsZero = aMethod.getParameterTypes().length == 0;

        return startsWithGet && !returnsVoid && parametersLenghIsZero;
    }

    public static boolean isIs(Method aMethod) {
        boolean startsWithIs          = aMethod.getName().startsWith("is"),
                returnsBoolean        = aMethod.getReturnType() == boolean.class,
                parametersLenghIsZero = aMethod.getParameterTypes().length == 0;

        return startsWithIs && returnsBoolean && parametersLenghIsZero;
    }

    public static boolean isSetter(Method aMethod) {
        boolean startsWithSet         = aMethod.getName().startsWith("set"),
                returnsVoid           = aMethod.getReturnType() == void.class,
                parametersLenghIsOne  = aMethod.getParameterTypes().length == 1;

        return startsWithSet && returnsVoid && parametersLenghIsOne;
    }

    private Constructor<?> getConstructor(Class<?> aClass, Object... objs) {
        for(Constructor<?> constructor : aClass.getConstructors()) {
            Class<?>[] params = constructor.getParameterTypes();

            if(params.length == objs.length) {
                boolean erro = false;
                for(int i = 0; i < objs.length && !erro; i++) {
                    if (!params[i].isInstance(objs[i])) {
                        erro = true;
                    }
                }
                if (!erro) {
                    return constructor;
                }
            }
        }
        throw new RuntimeException("Constructor not found!");
    }

}