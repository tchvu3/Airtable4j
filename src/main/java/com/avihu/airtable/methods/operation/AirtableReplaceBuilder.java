package com.avihu.airtable.methods.operation;

import com.avihu.airtable.Airtable;
import com.avihu.airtable.exception.AirtableException;
import com.avihu.airtable.methods.base.AirtableRequestBuilder;
import com.avihu.airtable.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable.model.generic.AirtableMappedObject;
import com.avihu.airtable.model.replace.external.AirtableRecordReplaceResponse;
import com.avihu.airtable.model.replace.external.AirtableReplaceResponse;
import com.avihu.airtable.model.replace.internal.AirtableInitialReplaceResponse;
import com.avihu.airtable.model.update.internal.output.AirtableUpdatePayload;
import com.avihu.airtable.reflection.AirtableObjectMapper;
import kong.unirest.HttpMethod;

import java.util.*;

public class AirtableReplaceBuilder<T> extends AirtableRequestBuilder<AirtableInitialReplaceResponse, AirtableReplaceResponse> {

    private final Airtable.InternalApi apiRef;
    private final AirtableReplaceBuilder<T>.PublicApi publicApi;
    private final AirtableReplaceBuilder<T>.DatabaseSetter databaseSetter;
    private final AirtableReplaceBuilder<T>.TableSetter tableSetter;

    @AirtableBuilderPath(0)
    private String database;
    @AirtableBuilderPath(1)
    private String table;

    private Set<AirtableMappedObject> records;

    public AirtableReplaceBuilder(Airtable.InternalApi apiRef) {
        this.apiRef = apiRef;
        this.publicApi = new PublicApi();
        this.databaseSetter = new DatabaseSetter();
        this.tableSetter = new TableSetter();
    }

    public static <T> AirtableReplaceBuilder<T>.DatabaseSetter buildInstance(Airtable.InternalApi apiRef) {
        return new AirtableReplaceBuilder<T>(apiRef).databaseSetter;
    }

    @Override
    public Class<AirtableInitialReplaceResponse> getInitialResponseClass() {
        return AirtableInitialReplaceResponse.class;
    }

    @Override
    public AirtableReplaceResponse transformInitial(AirtableInitialReplaceResponse source) {
        AirtableReplaceResponse toReturn = new AirtableReplaceResponse();
        List<AirtableRecordReplaceResponse> parsedRecords = new ArrayList<>();
        source.getRecords().forEach(record -> parsedRecords.add(new AirtableRecordReplaceResponse(record)));
        toReturn.setRecords(parsedRecords);
        return toReturn;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.PUT;
    }

    @Override
    public Object getRequestBody() {
        return new AirtableUpdatePayload(this.records);
    }

    public class DatabaseSetter {

        public AirtableReplaceBuilder<T>.TableSetter setDatabase(String databaseId) {
            AirtableReplaceBuilder.this.database = databaseId;
            return AirtableReplaceBuilder.this.tableSetter;
        }

    }

    public class TableSetter {

        public AirtableReplaceBuilder<T>.PublicApi setTable(String tableId) {
            AirtableReplaceBuilder.this.table = tableId;
            return AirtableReplaceBuilder.this.publicApi;
        }

    }

    public class PublicApi {

        public AirtableReplaceBuilder<T>.PublicApi setRecordsToUpdate(T... records) throws AirtableException {
            AirtableReplaceBuilder.this.records = new HashSet<>();
            this.addRecordsToUpdate(records);
            return this;
        }

        public AirtableReplaceBuilder<T>.PublicApi addRecordsToUpdate(T... records) throws AirtableException {
            if (AirtableReplaceBuilder.this.records == null) {
                AirtableReplaceBuilder.this.records = new HashSet<>();
            }
            for (T record : records) {
                AirtableReplaceBuilder.this.records.add(AirtableObjectMapper.toMap(record));
            }
            return this;
        }

        public AirtableReplaceBuilder<T>.PublicApi addRecordToUpdate(String id, Map<String, Object> fields) {
            if (AirtableReplaceBuilder.this.records == null) {
                AirtableReplaceBuilder.this.records = new HashSet<>();
            }
            AirtableReplaceBuilder.this.records.add(new AirtableMappedObject(id, fields));
            return this;
        }

        public AirtableReplaceResponse execute() throws AirtableException {
            return AirtableReplaceBuilder.this.apiRef.execute(AirtableReplaceBuilder.this);
        }

    }

}
