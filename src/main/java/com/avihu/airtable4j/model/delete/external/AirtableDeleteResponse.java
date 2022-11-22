package com.avihu.airtable4j.model.delete.external;

import java.util.List;

public class AirtableDeleteResponse {

    private List<AirtableRecordDeleteResponse> records;

    public List<AirtableRecordDeleteResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecordDeleteResponse> records) {
        this.records = records;
    }

}
