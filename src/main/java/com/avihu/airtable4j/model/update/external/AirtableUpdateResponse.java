package com.avihu.airtable4j.model.update.external;

import java.util.List;

public class AirtableUpdateResponse {

    private List<AirtableRecordUpdateResponse> records;

    public List<AirtableRecordUpdateResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecordUpdateResponse> records) {
        this.records = records;
    }

}
