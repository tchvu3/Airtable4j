package com.avihu.airtable4j.model.replace.internal;

import java.util.List;

public class AirtableInitialReplaceResponse {

    private List<AirtableInitialRecordReplaceResponse> records;

    public List<AirtableInitialRecordReplaceResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableInitialRecordReplaceResponse> records) {
        this.records = records;
    }

}
