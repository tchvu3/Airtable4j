package com.avihu.airtable.model.add.internal.input;


import java.util.List;

public class AirtableInitialAddResponse {

    private List<AirtableInitialRecordAddResponse> records;

    public List<AirtableInitialRecordAddResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableInitialRecordAddResponse> records) {
        this.records = records;
    }

}
