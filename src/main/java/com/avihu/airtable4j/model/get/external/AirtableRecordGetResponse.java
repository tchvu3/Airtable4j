package com.avihu.airtable4j.model.get.external;

import com.avihu.airtable4j.model.get.internal.AirtableInitialRecordGetResponse;
import com.avihu.airtable4j.types.AirtableType;
import com.avihu.airtable4j.types.AirtableTypeResolver;

import java.util.HashMap;
import java.util.Map;

public class AirtableRecordGetResponse {

    private String id;
    private String createdTime;
    private Map<String, AirtableRecordGetFieldResponse<?>> fields;

    public AirtableRecordGetResponse(AirtableInitialRecordGetResponse initial) {
        this.id = initial.getId();
        this.createdTime = initial.getCreatedTime();
        initial.getFields().forEach((key, value) -> {
            AirtableType airtableType = AirtableTypeResolver.inferType(value);
            this.addField(key, AirtableRecordGetFieldResponseFactory.generate(airtableType, value));
        });
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

    public Map<String, AirtableRecordGetFieldResponse<?>> getFields() {
        return fields;
    }

    public AirtableRecordGetFieldResponse<?> getField(String name) {
        try {
            Map.Entry<String, AirtableRecordGetFieldResponse<?>> result = this.fields.entrySet().stream().filter(entry -> entry.getKey().equals(name)).findFirst().orElse(null);
            return (result == null) ? null : result.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public void setFields(Map<String, AirtableRecordGetFieldResponse<?>> fields) {
        this.fields = fields;
    }

    public void addField(String key, AirtableRecordGetFieldResponse<?> value) {
        if (this.fields == null) {
            this.fields = new HashMap<>();
        }
        this.fields.put(key, value);
    }

}
