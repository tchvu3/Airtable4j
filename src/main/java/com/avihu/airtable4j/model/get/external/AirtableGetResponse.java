package com.avihu.airtable4j.model.get.external;

import com.avihu.airtable4j.model.get.internal.AirtableInitialGetResponse;

import java.util.List;

public class AirtableGetResponse {

    private List<AirtableRecordGetResponse> records;
    private String offset;

    public AirtableGetResponse(AirtableInitialGetResponse source) {
        this.offset = source.getOffset();
    }

    public List<AirtableRecordGetResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecordGetResponse> records) {
        this.records = records;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

}
