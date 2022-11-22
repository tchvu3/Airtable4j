package com.avihu.airtable4j.model.add.external;

import com.avihu.airtable4j.model.add.internal.input.AirtableInitialAddResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AirtableAddResponse {

    private List<AirtableRecordAddResponse> records;

    public AirtableAddResponse() {
    }

    public AirtableAddResponse(AirtableInitialAddResponse source) {
        this.records = source.getRecords().stream().map(AirtableRecordAddResponse::new).collect(Collectors.toList());
    }

    public List<AirtableRecordAddResponse> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecordAddResponse> records) {
        this.records = records;
    }

}
