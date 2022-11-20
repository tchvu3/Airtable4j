package com.avihu.airtable.model.add.internal.output;

import java.util.Map;

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
