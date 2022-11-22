package com.avihu.airtable4j.model.add.internal.output;

import java.util.HashSet;
import java.util.Set;

public class AirtableAddPayload<T> {

    private Set<AirtableAddEntryPayload<T>> records = new HashSet<>();

    public AirtableAddPayload(Set<T> records) {
        records.forEach(record -> this.records.add(new AirtableAddEntryPayload<>(record)));
    }

    public Set<AirtableAddEntryPayload<T>> getRecords() {
        return records;
    }

    public void setRecords(Set<AirtableAddEntryPayload<T>> records) {
        this.records = records;
    }
}
