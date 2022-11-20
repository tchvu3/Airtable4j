package com.avihu.airtable.model.generic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AirtableMappedObject {

    private String id;
    private String createdTimestamp;
    private Map<String, Object> values;

    public AirtableMappedObject() {
    }

    public AirtableMappedObject(String id, String createdTimestamp, Map<String, Object> values) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.values = values;
    }

    public AirtableMappedObject(String id, Map<String, Object> values) {
        this.id = id;
        this.values = values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public void addValue(String key, Object value) {
        if (this.values == null) {
            this.values = new HashMap<>();
        }
        this.values.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirtableMappedObject that = (AirtableMappedObject) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
