package com.avihu.airtable4j.methods.base.urlbuild;

import com.avihu.airtable4j.utils.AirtableTypingUtils;

import java.lang.reflect.Field;

public class CustomField {

    private final Field field;
    private final Object value;
    private final boolean required;
    private final String fieldName;

    public CustomField(Field field, Object value, boolean required, String fieldName) {
        this.field = field;
        this.value = value;
        this.required = required;
        this.fieldName = fieldName;
    }

    public Field getField() {
        return this.field;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean isRequired() {
        return this.required;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getValueAsString() {
        return (this.value == null) ? null : this.value.toString().trim();
    }

    public boolean isValueInvalidAsString() {
        return AirtableTypingUtils.isNotStringOrEmpty(this.getValueAsString());
    }

    public boolean isValueValidAsString() {
        return AirtableTypingUtils.isNonEmptyString(this.getValueAsString());
    }

    public boolean isRequiredButInvalidAsString() {
        return this.isRequired() && this.isValueInvalidAsString();
    }

}
