package com.avihu.airtable.model.update.internal.input;

import java.util.List;

public class AirtableInitialUpdateResponse {

    private List<AirtableInitialRecordUpdateResponse> records;

    public List<AirtableInitialRecordUpdateResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableInitialRecordUpdateResponse> records) {
        this.records = records;
    }

}
