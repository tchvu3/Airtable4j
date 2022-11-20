package com.avihu.airtable.model.update.internal.output;

import com.avihu.airtable.model.generic.AirtableMappedObject;

import java.util.HashSet;
import java.util.Set;

public class AirtableUpdatePayload {

    private Set<AirtableUpdateEntryPayload> records = new HashSet<>();

    public AirtableUpdatePayload(Set<AirtableMappedObject> records) {
        records.forEach(record -> this.records.add(new AirtableUpdateEntryPayload(record.getId(), record.getValues())));
    }

    public Set<AirtableUpdateEntryPayload> getRecords() {
        return records;
    }

    public void setRecords(Set<AirtableUpdateEntryPayload> records) {
        this.records = records;
    }

}
