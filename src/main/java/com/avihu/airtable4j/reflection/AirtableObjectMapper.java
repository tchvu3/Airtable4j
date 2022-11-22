package com.avihu.airtable4j.reflection;

import com.avihu.airtable4j.annotation.AirtableField;
import com.avihu.airtable4j.annotation.AirtableRowCreatedTime;
import com.avihu.airtable4j.annotation.AirtableRowId;
import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.model.generic.AirtableMappedObject;
import com.avihu.airtable4j.model.get.external.AirtableRecordGetFieldResponse;
import com.avihu.airtable4j.model.get.external.AirtableRecordGetResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public abstract class AirtableObjectMapper {

    public static <T> T toObject(AirtableRecordGetResponse record, Class<T> toClass) throws AirtableException {
        try {
            Constructor<T> constructor = toClass.getConstructor();
            T instance = constructor.newInstance();
            Field[] fields = toClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AirtableRowId.class)) {
                    AirtableObjectMapper.setFieldForObjectInstance(instance, field, record.getId());
                } else if (field.isAnnotationPresent(AirtableRowCreatedTime.class)) {
                    AirtableObjectMapper.setFieldForObjectInstance(instance, field, record.getCreatedTime());
                } else if (field.isAnnotationPresent(AirtableField.class)) {
                    injectAirtableField(record, instance, field);
                } else {
                    injectGenericField(record, instance, field);
                }
            }
            return instance;
        } catch (Exception exception) {
            throw new AirtableException(exception);
        }
    }

    public static AirtableMappedObject toMap(Object object) throws AirtableException {
        return AirtableObjectMapper.toMap(object, true);
    }

    public static AirtableMappedObject toMap(Object object, boolean ignoreNull) throws AirtableException {
        try {
            AirtableMappedObject toReturn = new AirtableMappedObject();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                Object value = AirtableObjectMapper.getFieldValueFromObjectInstance(object, field);
                if (ignoreNull && value == null) {
                    continue;
                }
                if (field.isAnnotationPresent(AirtableRowId.class)) {
                    toReturn.setId(value.toString());
                } else if (field.isAnnotationPresent(AirtableRowCreatedTime.class)) {
                    toReturn.setCreatedTimestamp(value.toString());
                } else if (field.isAnnotationPresent(AirtableField.class)) {
                    AirtableField annotation = field.getAnnotation(AirtableField.class);
                    toReturn.addValue(annotation.value(), value);
                } else {
                    toReturn.addValue(field.getName(), value);
                }
            }
            return toReturn;
        } catch (Exception exception) {
            throw new AirtableException(exception);
        }
    }

    private static <T> void injectAirtableField(AirtableRecordGetResponse record, T instance, Field field) throws IllegalAccessException {
        AirtableField annotation = field.getAnnotation(AirtableField.class);
        AirtableRecordGetFieldResponse<?> recordField = record.getField(annotation.value());
        if (recordField != null) {
            AirtableObjectMapper.setFieldForObjectInstance(instance, field, recordField.getValue());
        }
    }

    private static <T> void injectGenericField(AirtableRecordGetResponse record, T instance, Field field) throws IllegalAccessException {
        AirtableRecordGetFieldResponse<?> recordField = record.getField(field.getName());
        if (recordField != null) {
            AirtableObjectMapper.setFieldForObjectInstance(instance, field, recordField.getValue());
        }
    }

    private static <T> void setFieldForObjectInstance(T instance, Field field, Object value) throws IllegalAccessException {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } finally {
            field.setAccessible(false);
        }
    }

    private static <T, V> V getFieldValueFromObjectInstance(T instance, Field field) throws IllegalAccessException {
        try {
            field.setAccessible(true);
            return (V) field.get(instance);
        } finally {
            field.setAccessible(false);
        }
    }

}
