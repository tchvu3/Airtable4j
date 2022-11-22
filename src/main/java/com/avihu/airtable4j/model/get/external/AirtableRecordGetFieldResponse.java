package com.avihu.airtable4j.model.get.external;

import com.avihu.airtable4j.types.AirtableType;

public class AirtableRecordGetFieldResponse<T> {

    private AirtableType type;
    private T value;

    public AirtableRecordGetFieldResponse(AirtableType type) {
        this.type = type;
    }

    public AirtableType getType() {
        return type;
    }

    public void setType(AirtableType type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
