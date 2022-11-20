package com.avihu.airtable.model.update.external;

import com.avihu.airtable.model.get.external.AirtableRecordGetResponse;
import com.avihu.airtable.model.get.internal.AirtableInitialGetResponse;

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
