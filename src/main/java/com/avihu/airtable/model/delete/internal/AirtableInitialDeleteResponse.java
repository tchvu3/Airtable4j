package com.avihu.airtable.model.delete.internal;

import java.util.List;

public class AirtableInitialDeleteResponse {

    private List<AirtableInitialRecordDeleteResponse> records;

    public List<AirtableInitialRecordDeleteResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableInitialRecordDeleteResponse> records) {
        this.records = records;
    }
}
