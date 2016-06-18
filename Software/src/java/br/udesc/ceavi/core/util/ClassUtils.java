package br.udesc.ceavi.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Samuel Felício Adriano
 * @since 04/06/2016
 */
public class ClassUtils {

    public final static <Return> Return getNewInstance(Class aClass, Object... parametersTypes) {
        for(Constructor<?> constructor : aClass.getConstructors()) {
            Class<?>[] constructorParameters = constructor.getParameterTypes();

            if(parametersTypes.length != constructorParameters.length) {
                continue;
            }

            boolean ok = true;

//            for(int i = 0; i < parametersTypes.length && ok; i++) {
//                if(!constructorParameters[i].isInstance(parametersTypes[i].getClass())) {
//                    ok = false;
//                }
//            }

            if(ok) {
                try {
                    return (Return) constructor.newInstance(parametersTypes);
                } catch(InstantiationException   | IllegalAccessException |
                        IllegalArgumentException | InvocationTargetException exception) {
                    throw new RuntimeException(exception.getMessage());
                }
            }
        }

        throw new RuntimeException("Constructor not found");
    }

    public final static <Return> Return getNewPOJO(Class aClass) {
        return getNewInstance(aClass, new Object[0]);

    }

}