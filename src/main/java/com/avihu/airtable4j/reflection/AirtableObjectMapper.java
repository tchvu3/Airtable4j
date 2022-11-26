package com.avihu.airtable4j.reflection;

import com.avihu.airtable4j.annotation.AirtableField;
import com.avihu.airtable4j.annotation.AirtableRowCreatedTime;
import com.avihu.airtable4j.annotation.AirtableRowId;
import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.model.generic.AirtableMappedObject;
import com.avihu.airtable4j.model.get.external.AirtableRecordGetFieldResponse;
import com.avihu.airtable4j.model.get.external.AirtableRecordGetResponse;
import com.avihu.airtable4j.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public abstract class AirtableObjectMapper {

    public static <T> T toObject(AirtableRecordGetResponse record, Class<T> toClass) throws AirtableException {
        try {
            Constructor<T> constructor = toClass.getConstructor();
            T instance = constructor.newInstance();
            Field[] fields = toClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AirtableRowId.class)) {
                    ReflectionUtils.setFieldValueOfObject(instance, field, record.getId());
                } else if (field.isAnnotationPresent(AirtableRowCreatedTime.class)) {
                    ReflectionUtils.setFieldValueOfObject(instance, field, record.getCreatedTime());
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
                Object value = ReflectionUtils.getParsedFieldValueFromObject(object, field);
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

    private static <T> void injectAirtableField(AirtableRecordGetResponse record, T instance, Field field) {
        AirtableField annotation = field.getAnnotation(AirtableField.class);
        AirtableRecordGetFieldResponse<?> recordField = record.getField(annotation.value());
        if (recordField != null) {
            ReflectionUtils.setFieldValueOfObject(instance, field, recordField.getValue());
        }
    }

    private static <T> void injectGenericField(AirtableRecordGetResponse record, T instance, Field field) {
        AirtableRecordGetFieldResponse<?> recordField = record.getField(field.getName());
        if (recordField != null) {
            ReflectionUtils.setFieldValueOfObject(instance, field, recordField.getValue());
        }
    }

}
