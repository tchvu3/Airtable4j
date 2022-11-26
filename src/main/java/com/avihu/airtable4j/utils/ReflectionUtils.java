package com.avihu.airtable4j.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;
import java.util.function.Function;

public abstract class ReflectionUtils {

    /* The "declared" versions WILL WORK on private fields */

    public static Object getDeclaredFieldValueFromObject(Object object, Field field) {
        return AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
            try {
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception ignore) {
            } finally {
                field.setAccessible(false);
            }
            return null;
        });
    }

    public static <T, V> V getDeclaredParsedFieldValueFromObject(T instance, Field field) {
        return (V) ReflectionUtils.getDeclaredFieldValueFromObject(instance, field);
    }

    public static <T> void setDeclaredFieldValueOfObject(T instance, Field field, Object value) {
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            try {
                field.setAccessible(true);
                field.set(instance, value);
            } catch (Exception ignore) {
            } finally {
                field.setAccessible(false);
            }
            return null;
        });
    }

    /* The "regular" versions WILL NOT WORK on private fields */

    public static Object getFieldValueFromObject(Object object, Field field) {
        try {
            return field.get(object);
        } catch (Exception ignore) {
        }
        try {
            return Objects.requireNonNull(ReflectionUtils.findGetter(field)).invoke(object);
        } catch (Exception ignore) {
        }
        return null;
    }

    public static <T, V> V getParsedFieldValueFromObject(T instance, Field field) {
        return (V) ReflectionUtils.getFieldValueFromObject(instance, field);
    }

    public static <T> void setFieldValueOfObject(T instance, Field field, Object value) {
        try {
            field.set(instance, value);
            return;
        } catch (Exception ignore) {
        }
        try {
            Objects.requireNonNull(ReflectionUtils.findSetter(field)).invoke(instance, value);
        } catch (Exception ignore) {
        }
    }

    public static Method findGetter(Field field) {
        try {
            return ReflectionUtils.fetchFromPropertyDescriptorOfField(field, PropertyDescriptor::getReadMethod);
        } catch (Exception ignore) {
        }
        return null;
    }

    public static Method findSetter(Field field) {
        try {
            return ReflectionUtils.fetchFromPropertyDescriptorOfField(field, PropertyDescriptor::getWriteMethod);
        } catch (Exception ignore) {
        }
        return null;
    }

    private static Method fetchFromPropertyDescriptorOfField(Field field, Function<PropertyDescriptor, Method> logic) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(field.getDeclaringClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if (propertyDescriptor.getName().equals(field.getName())) {
                    return logic.apply(propertyDescriptor);
                }
            }
        } catch (Exception ignore) {
        }
        return null;
    }

}
