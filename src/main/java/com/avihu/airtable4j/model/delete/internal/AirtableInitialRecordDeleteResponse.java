package com.avihu.airtable4j.model.delete.internal;

public class AirtableInitialRecordDeleteResponse {

    private String id;
    private boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
