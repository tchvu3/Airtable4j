package com.avihu.airtable.utils;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class AirtableTypingUtils {

    // type conversion

    public static String toString(Object value) {
        return AirtableTypingUtils.toString(() -> value);
    }

    public static String toString(Callable<Object> callable) {
        try {
            Object result = callable.call();
            return result == null ? null : String.valueOf(result);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static Boolean toBoolean(Object value) {
        return AirtableTypingUtils.toBoolean(() -> value);
    }

    public static Boolean toBoolean(Callable<Object> callable) {
        try {
            Object result = callable.call();
            String strValue = String.valueOf(result);
            return Boolean.parseBoolean(strValue);
        } catch (Exception ignore) {
            return false;
        }
    }

    public static Long toLong(Object value) {
        return AirtableTypingUtils.toLong(() -> value);
    }

    public static Long toLong(Callable<Object> callable) {
        try {
            Object result = callable.call();
            String strValue = String.valueOf(result);
            if (strValue.contains(".")) {
                return new Double(Double.parseDouble(String.valueOf(result))).longValue();
            } else {
                return Long.parseLong(String.valueOf(result));
            }
        } catch (Exception ignore) {
            return 0L;
        }
    }

    public static Double toDouble(Object value) {
        return AirtableTypingUtils.toDouble(() -> value);
    }

    public static Double toDouble(Callable<Object> callable) {
        try {
            Object result = callable.call();
            return Double.parseDouble(String.valueOf(result));
        } catch (Exception ignore) {
            return 0.0;
        }
    }

    // types testing

    public static boolean isOfClass(Object valOrType, Class<?> cls) {
        return valOrType != null && (valOrType.getClass().equals(cls) || valOrType.equals(cls));
    }

    public static boolean isPrimitiveCollection(Collection<?> collection) {
        if (collection == null || collection.size() <= 0) {
            return true;
        }
        Object element = collection.iterator().next();
        if (element == null) {
            return false;
        }
        return AirtableTypingUtils.isPrimitiveOrBoxed(element);
    }

    public static boolean isPrimitiveOrBoxed(Object toInfer) {
        return AirtableTypingUtils.isString(toInfer)
                || AirtableTypingUtils.isByte(toInfer)
                || AirtableTypingUtils.isShort(toInfer)
                || AirtableTypingUtils.isInt(toInfer)
                || AirtableTypingUtils.isLong(toInfer)
                || AirtableTypingUtils.isFloat(toInfer)
                || AirtableTypingUtils.isDouble(toInfer)
                || AirtableTypingUtils.isChar(toInfer)
                || AirtableTypingUtils.isBoolean(toInfer);
    }

    public static boolean isPrimitiveOrBoxed(Object toInfer, Class<?> primitiveClass, Class<?> boxedClass) {
        return toInfer != null && (toInfer.getClass() == primitiveClass || toInfer.getClass().equals(boxedClass) || toInfer == primitiveClass || toInfer.equals(boxedClass));
    }

    public static boolean isList(Object toTest) {
        return toTest instanceof List;
    }

    public static boolean isLinkedTreeMap(Object toTest) {
        return toTest instanceof LinkedTreeMap;
    }

    public static boolean isNonEmptyCollection(Object toTest) {
        return toTest instanceof Collection<?> && ((Collection<?>) toTest).size() > 0;
    }

    public static boolean isNotCollectionOrEmpty(Object toTest) {
        return !AirtableTypingUtils.isNonEmptyCollection(toTest);
    }

    public static boolean isByte(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Byte.TYPE, Byte.class);
    }

    public static boolean isShort(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Short.TYPE, Short.class);
    }

    public static boolean isInt(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Integer.TYPE, Integer.class);
    }

    public static boolean isLong(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Long.TYPE, Long.class);
    }

    public static boolean isFloat(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Float.TYPE, Float.class);
    }

    public static boolean isDouble(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Double.TYPE, Double.class);
    }

    public static boolean isChar(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Character.TYPE, Character.class);
    }

    public static boolean isBoolean(Object toTest) {
        return AirtableTypingUtils.isPrimitiveOrBoxed(toTest, Boolean.TYPE, Boolean.class);
    }

    public static boolean isIntOrLong(Object toTest) {
        return AirtableTypingUtils.isInt(toTest) || AirtableTypingUtils.isLong(toTest);
    }

    public static boolean isFloatOrDouble(Object toTest) {
        return AirtableTypingUtils.isFloat(toTest) || AirtableTypingUtils.isDouble(toTest);
    }

    public static boolean isString(Object toTest) {
        return AirtableTypingUtils.isOfClass(toTest, String.class);
    }

    public static boolean isNonEmptyString(Object toTest) {
        return AirtableTypingUtils.isString(toTest) && toTest.toString().trim().length() > 0;
    }

    public static boolean isNotStringOrEmpty(Object toTest) {
        return !AirtableTypingUtils.isNonEmptyString(toTest);
    }

}
