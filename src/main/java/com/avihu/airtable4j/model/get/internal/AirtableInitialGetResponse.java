package com.avihu.airtable4j.model.get.internal;

import java.util.List;

public class AirtableInitialGetResponse {

    private List<AirtableInitialRecordGetResponse> records;
    private String offset;

    public List<AirtableInitialRecordGetResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableInitialRecordGetResponse> records) {
        this.records = records;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
