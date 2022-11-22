package com.avihu.airtable4j.model.add.internal.output;

public class AirtableAddEntryPayload<T> {

    private T fields;

    public AirtableAddEntryPayload(T fields) {
        this.fields = fields;
    }

    public T getFields() {
        return fields;
    }

    public void setFields(T fields) {
        this.fields = fields;
    }
}
