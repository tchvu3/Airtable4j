package com.avihu.airtable.model.replace.external;

import com.avihu.airtable.model.get.external.AirtableRecordGetFieldResponse;
import com.avihu.airtable.model.get.external.AirtableRecordGetFieldResponseFactory;
import com.avihu.airtable.model.replace.internal.AirtableInitialRecordReplaceResponse;
import com.avihu.airtable.types.AirtableType;
import com.avihu.airtable.types.AirtableTypeResolver;

import java.util.HashMap;
import java.util.Map;

public class AirtableRecordReplaceResponse {


    private String id;
    private String createdTime;
    private Map<String, AirtableRecordGetFieldResponse<?>> fields;

    public AirtableRecordReplaceResponse(AirtableInitialRecordReplaceResponse initial) {
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
