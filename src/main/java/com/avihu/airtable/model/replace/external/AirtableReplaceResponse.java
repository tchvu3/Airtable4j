package com.avihu.airtable.model.replace.external;

import java.util.List;

public class AirtableReplaceResponse {

    private List<AirtableRecordReplaceResponse> records;

    public List<AirtableRecordReplaceResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecordReplaceResponse> records) {
        this.records = records;
    }

}
