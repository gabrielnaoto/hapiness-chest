package br.udesc.ceavi.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Utils operations for Beans
 *
 * @author Samuel Felício Adriano <felicio.samuel@gmail.com>
 * @since  08/05/2016
 */
public class BeanUtils {

    /**
     * Calls the "set" method of a property
     *
     * @param bean           - Class that has the property
     * @param propertyName   - The name of the property to be setted
     * @param value          - The value to be setted in the property
     * @param throwException - Indicates if the method should throw an Exception if the Method not be found
     */
    public static void callSetter(Object bean, String propertyName, Object value, boolean throwException) {
        String[] matcher = extractAssossiationsFromMethod(propertyName);

        if(matcher.length > 1) {
            Object association = callGetter(bean, matcher[0], throwException);
            callSetter(association, matcher[1], value, throwException);
            return;
        }

        Iterator<Class> possibleClassesIterator = getPossibleClasses(value.getClass());

        while(possibleClassesIterator.hasNext()) {
            try {
                String methodName = getSetterName(propertyName);
                Method method     = bean.getClass().getMethod(methodName, possibleClassesIterator.next());
                if(MethodUtils.isSetter(method)) {
                    method.invoke(bean, value);
                    return;
                }
                throw new RuntimeException("Invalid setter method");
            } catch(Exception exception) {
                if(exception instanceof java.lang.NoSuchMethodException) {
                    continue;
                }

                throw new RuntimeException(exception.getMessage());
            }
        }
        if(throwException) {
            throw new RuntimeException("Setter method not found.");
        }
    }


    /**
     * Calls the "set" method of a property.
     * This method don't throw an Exception if the Method isn't found.
     *
     * @param bean           - Class that has the property
     * @param propertyName   - The name of the property to be setted
     * @param value          - The value to be setted in the property
     */
    public static void callSetter(Object bean, String propertyName, Object value) {
        callSetter(bean, propertyName, value, false);
    }

    /**
     * Calls the "get" method of a property
     *
     * @param <Return>       - The class of the property
     *
     * @param bean           - The Bean class
     * @param propertyName   - The name of the property to be getted
     * @param throwException - Indicates if the method should throw an Exception if the Method not be found
     *
     * @return the value of the called property
     */
    public static <Return> Return callGetter(Object bean, String propertyName, boolean throwException) {
        String[] matcher = extractAssossiationsFromMethod(propertyName);

        if(matcher.length > 1) {
            Object association = callGetter(bean, matcher[0], throwException);
            return callGetter(association, matcher[1], throwException);
        }

        try {
            String methodName = getGetterName(propertyName);
            Method method     = bean.getClass().getMethod(methodName);
            if(MethodUtils.isGetter(method)) {
                return (Return) method.invoke(bean);
            }
            throw new RuntimeException("Invalid getter method");
        } catch (NoSuchMethodException noMethodException) {
            if(throwException) {
                throw new RuntimeException(noMethodException.getMessage());
            }
            return null;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Calls the "get" method of a property.
     * This method don't throw an Exception if the Method isn't found.
     *
     * @param <Return>       - The class of the property
     *
     * @param bean           - The Bean class
     * @param propertyName   - The name of the property to be getted
     *
     * @return the value of the called property
     */
    public static <Return> Return callGetter(Object bean, String propertyName) {
        return callGetter(bean, propertyName, false);
    }

    /**
     * Calls the "is" method of a property
     *
     * @param bean           - The Bean class
     * @param propertyName   - The name of the property to be getted
     * @param throwException - Indicates if the method should throw an Exception if the Method not be found
     *
     * @return the value of the called property
     */
    public static boolean callIs(Object bean, String propertyName, boolean throwException) {
        String[] matcher = extractAssossiationsFromMethod(propertyName);

        if(matcher.length > 1) {
            bean = callGetter(bean, matcher[0], throwException);
            return callIs(bean, matcher[1], throwException);
        }

        try {
            String methodName = getIsName(propertyName);
            Method method     = bean.getClass().getMethod(methodName);
            if(MethodUtils.isIs(method)) {
                return (boolean) method.invoke(bean);
            }
            throw new RuntimeException("Invalid is method");
        } catch (NoSuchMethodException noMethodException) {
            if(throwException) {
                throw new RuntimeException(noMethodException.getMessage());
            }
            return false;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Calls the "is" method of a property.
     * This method don't throw an Exception if the Method isn't found.
     *
     * @param bean           - The Bean class
     * @param propertyName   - The name of the property to be getted
     *
     * @return the value of the called property
     */
    public static boolean callIs(Object bean, String propertyName) {
        return callIs(bean, propertyName, false);
    }

    public static String getIsName(String property) {
        return "is" + StringUtils.toCamelCase(property, true);
    }

    public static String getGetterName(String property) {
        return "get" + StringUtils.toCamelCase(property, true);
    }

    public static String getSetterName(String property) {
        return "set" + StringUtils.toCamelCase(property, true);
    }

    private static Iterator<Class> getPossibleClasses(Class aClass) {
        java.util.List<Class> possibleClasses = new java.util.ArrayList<>();

        switch(aClass.getName()) {
            case "java.lang.Long":
                possibleClasses.add(aClass);
                possibleClasses.add(long.class);
                possibleClasses.add(int.class);
                possibleClasses.add(Integer.class);
            break;

            case "java.lang.Integer":
                possibleClasses.add(aClass);
                possibleClasses.add(int.class);
                possibleClasses.add(long.class);
                possibleClasses.add(Long.class);
            break;

            case "java.lang.Double":
                possibleClasses.add(aClass);
                possibleClasses.add(double.class);
                possibleClasses.add(Double.class);
            break;

            default:
                possibleClasses.add(aClass);
        }

        possibleClasses.addAll(Arrays.asList(aClass.getInterfaces()));

        Class superClass = aClass.getSuperclass();
        while(superClass != null) {
            possibleClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }

        return possibleClasses.iterator();
    }

    private static String[] extractAssossiationsFromMethod(String method, int limit) {
        return method.split(Pattern.quote("."), limit);
    }

    private static String[] extractAssossiationsFromMethod(String method) {
        return extractAssossiationsFromMethod(method, 0);
    }

}