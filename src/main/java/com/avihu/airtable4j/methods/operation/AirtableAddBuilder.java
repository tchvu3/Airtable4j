package com.avihu.airtable4j.methods.operation;

import com.avihu.airtable4j.Airtable;
import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable4j.methods.base.AirtableRequestBuilder;
import com.avihu.airtable4j.model.add.external.AirtableAddResponse;
import com.avihu.airtable4j.model.add.internal.input.AirtableInitialAddResponse;
import com.avihu.airtable4j.model.add.internal.output.AirtableAddPayload;
import kong.unirest.HttpMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AirtableAddBuilder<T> extends AirtableRequestBuilder<AirtableInitialAddResponse, AirtableAddResponse> {

    private final Airtable.InternalApi apiRef;
    private final AirtableAddBuilder<T>.PublicApi publicApi;
    private final AirtableAddBuilder<T>.DatabaseSetter databaseSetter;
    private final AirtableAddBuilder<T>.TableSetter tableSetter;


    @AirtableBuilderPath(0)
    private String database;
    @AirtableBuilderPath(1)
    private String table;

    private Set<T> records;

    private AirtableAddBuilder(Airtable.InternalApi apiRef) {
        this.apiRef = apiRef;
        this.publicApi = new PublicApi();
        this.databaseSetter = new DatabaseSetter();
        this.tableSetter = new TableSetter();
    }

    public static <T> AirtableAddBuilder<T>.DatabaseSetter buildInstance(Airtable.InternalApi apiRef) {
        return new AirtableAddBuilder<T>(apiRef).databaseSetter;
    }

    @Override
    public Class<AirtableInitialAddResponse> getInitialResponseClass() {
        return AirtableInitialAddResponse.class;
    }

    @Override
    public AirtableAddResponse transformInitial(AirtableInitialAddResponse source) {
        return new AirtableAddResponse(source);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Object getRequestBody() {
        return new AirtableAddPayload<>(this.records);
    }


    public class DatabaseSetter {

        public AirtableAddBuilder<T>.TableSetter setDatabase(String databaseId) {
            AirtableAddBuilder.this.database = databaseId;
            return AirtableAddBuilder.this.tableSetter;
        }

    }

    public class TableSetter {

        public AirtableAddBuilder<T>.PublicApi setTable(String tableId) {
            AirtableAddBuilder.this.table = tableId;
            return AirtableAddBuilder.this.publicApi;
        }

    }

    public class PublicApi {

        public AirtableAddBuilder<T>.PublicApi setRecords(T... records) {
            AirtableAddBuilder.this.records = new HashSet<>();
            AirtableAddBuilder.this.records.addAll(Arrays.asList(records));
            return this;
        }

        public AirtableAddBuilder<T>.PublicApi addRecords(T... records) {
            if (AirtableAddBuilder.this.records == null) {
                AirtableAddBuilder.this.records = new HashSet<>();
            }
            AirtableAddBuilder.this.records.addAll(Arrays.asList(records));
            return this;
        }

        public AirtableAddResponse execute() throws AirtableException {
            return AirtableAddBuilder.this.apiRef.execute(AirtableAddBuilder.this);
        }

    }

}
