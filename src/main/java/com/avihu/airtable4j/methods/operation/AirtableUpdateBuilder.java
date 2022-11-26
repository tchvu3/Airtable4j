package com.avihu.airtable4j.methods.operation;

import com.avihu.airtable4j.Airtable;
import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable4j.methods.base.AirtableRequestBuilder;
import com.avihu.airtable4j.model.generic.AirtableMappedObject;
import com.avihu.airtable4j.model.update.external.AirtableRecordUpdateResponse;
import com.avihu.airtable4j.model.update.external.AirtableUpdateResponse;
import com.avihu.airtable4j.model.update.internal.input.AirtableInitialUpdateResponse;
import com.avihu.airtable4j.model.update.internal.output.AirtableUpdatePayload;
import com.avihu.airtable4j.reflection.AirtableObjectMapper;
import kong.unirest.HttpMethod;

import java.util.*;

public class AirtableUpdateBuilder<T> extends AirtableRequestBuilder<AirtableInitialUpdateResponse, AirtableUpdateResponse> {

    private final Airtable.InternalApi apiRef;
    private final AirtableUpdateBuilder<T>.PublicApi publicApi;
    private final AirtableUpdateBuilder<T>.DatabaseSetter databaseSetter;
    private final AirtableUpdateBuilder<T>.TableSetter tableSetter;

    @AirtableBuilderPath(0)
    private String database;
    @AirtableBuilderPath(1)
    private String table;

    private Set<AirtableMappedObject> records;

    private AirtableUpdateBuilder(Airtable.InternalApi apiRef) {
        this.apiRef = apiRef;
        this.publicApi = new PublicApi();
        this.databaseSetter = new DatabaseSetter();
        this.tableSetter = new TableSetter();
    }

    public static <T> AirtableUpdateBuilder<T>.DatabaseSetter buildInstance(Airtable.InternalApi apiRef) {
        return new AirtableUpdateBuilder<T>(apiRef).databaseSetter;
    }

    @Override
    public Class<AirtableInitialUpdateResponse> getInitialResponseClass() {
        return AirtableInitialUpdateResponse.class;
    }

    @Override
    public AirtableUpdateResponse transformInitial(AirtableInitialUpdateResponse source) {
        AirtableUpdateResponse toReturn = new AirtableUpdateResponse();
        List<AirtableRecordUpdateResponse> parsedRecords = new ArrayList<>();
        source.getRecords().forEach(record -> parsedRecords.add(new AirtableRecordUpdateResponse(record)));
        toReturn.setRecords(parsedRecords);
        return toReturn;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.PATCH;
    }

    @Override
    public Object getRequestBody() {
        return new AirtableUpdatePayload(this.records);
    }

    public class DatabaseSetter {

        public AirtableUpdateBuilder<T>.TableSetter setDatabase(String databaseId) {
            AirtableUpdateBuilder.this.database = databaseId;
            return AirtableUpdateBuilder.this.tableSetter;
        }

    }

    public class TableSetter {

        public AirtableUpdateBuilder<T>.PublicApi setTable(String tableId) {
            AirtableUpdateBuilder.this.table = tableId;
            return AirtableUpdateBuilder.this.publicApi;
        }

    }

    public class PublicApi {

        public AirtableUpdateBuilder<T>.PublicApi setRecordsToUpdate(T... records) throws AirtableException {
            AirtableUpdateBuilder.this.records = new HashSet<>();
            this.addRecordsToUpdate(records);
            return this;
        }

        public AirtableUpdateBuilder<T>.PublicApi addRecordsToUpdate(T... records) throws AirtableException {
            if (AirtableUpdateBuilder.this.records == null) {
                AirtableUpdateBuilder.this.records = new HashSet<>();
            }
            for (T record : records) {
                AirtableUpdateBuilder.this.records.add(AirtableObjectMapper.toMap(record));
            }
            return this;
        }

        public AirtableUpdateBuilder<T>.PublicApi addRecordToUpdate(String id, Map<String, Object> fields) {
            if (AirtableUpdateBuilder.this.records == null) {
                AirtableUpdateBuilder.this.records = new HashSet<>();
            }
            AirtableUpdateBuilder.this.records.add(new AirtableMappedObject(id, fields));
            return this;
        }

        public AirtableUpdateResponse execute() throws AirtableException {
            return AirtableUpdateBuilder.this.apiRef.execute(AirtableUpdateBuilder.this);
        }

    }

}
