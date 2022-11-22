package com.avihu.airtable4j.model.add.external;

import com.avihu.airtable4j.model.add.internal.input.AirtableInitialRecordAddResponse;

import java.util.Map;

public class AirtableRecordAddResponse {

    private String id;
    private String createdTime;
    private Map<String, Object> fields;

    public AirtableRecordAddResponse(AirtableInitialRecordAddResponse record) {
        this.id = record.getId();
        this.createdTime = record.getCreatedTime();
        this.fields = record.getFields();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }


}
