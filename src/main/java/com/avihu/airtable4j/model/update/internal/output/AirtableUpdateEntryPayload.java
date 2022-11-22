package com.avihu.airtable4j.model.update.internal.output;

public class AirtableUpdateEntryPayload {

    private String id;
    private Object fields;

    public AirtableUpdateEntryPayload(String id, Object fields) {
        this.id = id;
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getFields() {
        return fields;
    }

    public void setFields(Object fields) {
        this.fields = fields;
    }
}
