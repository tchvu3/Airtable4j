package com.avihu.airtable.model.delete.external;

import com.avihu.airtable.model.delete.internal.AirtableInitialRecordDeleteResponse;

public class AirtableRecordDeleteResponse {

    private String id;
    private boolean deleted;

    public AirtableRecordDeleteResponse(AirtableInitialRecordDeleteResponse airtableInitialRecordDeleteResponse) {
        this.id = airtableInitialRecordDeleteResponse.getId();
        this.deleted = airtableInitialRecordDeleteResponse.isDeleted();
    }

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
