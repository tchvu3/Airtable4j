package com.avihu.airtable4j.methods.operation;

import com.avihu.airtable4j.Airtable;
import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderQueryList;
import com.avihu.airtable4j.methods.base.AirtableRequestBuilder;
import com.avihu.airtable4j.model.delete.external.AirtableDeleteResponse;
import com.avihu.airtable4j.model.delete.external.AirtableRecordDeleteResponse;
import com.avihu.airtable4j.model.delete.internal.AirtableInitialDeleteResponse;
import kong.unirest.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AirtableDeleteBuilder extends AirtableRequestBuilder<AirtableInitialDeleteResponse, AirtableDeleteResponse> {

    private final Airtable.InternalApi apiRef;
    private final AirtableDeleteBuilder.PublicApi publicApi;
    private final AirtableDeleteBuilder.DatabaseSetter databaseSetter;
    private final AirtableDeleteBuilder.TableSetter tableSetter;

    @AirtableBuilderPath(0)
    private String database;
    @AirtableBuilderPath(1)
    private String table;
    @AirtableBuilderQueryList("records")
    private List<String> ids = new ArrayList<>();

    private AirtableDeleteBuilder(Airtable.InternalApi apiRef) {
        this.apiRef = apiRef;
        this.publicApi = new PublicApi();
        this.databaseSetter = new DatabaseSetter();
        this.tableSetter = new TableSetter();
    }

    public static AirtableDeleteBuilder.DatabaseSetter buildInstance(Airtable.InternalApi apiRef) {
        return new AirtableDeleteBuilder(apiRef).databaseSetter;
    }

    @Override
    public Class<AirtableInitialDeleteResponse> getInitialResponseClass() {
        return AirtableInitialDeleteResponse.class;
    }

    @Override
    public AirtableDeleteResponse transformInitial(AirtableInitialDeleteResponse source) {
        AirtableDeleteResponse toReturn = new AirtableDeleteResponse();
        List<AirtableRecordDeleteResponse> records = source.getRecords().stream().map(AirtableRecordDeleteResponse::new).collect(Collectors.toList());
        toReturn.setRecords(records);
        return toReturn;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    public class DatabaseSetter {

        public AirtableDeleteBuilder.TableSetter setDatabase(String databaseId) {
            AirtableDeleteBuilder.this.database = databaseId;
            return AirtableDeleteBuilder.this.tableSetter;
        }

    }

    public class TableSetter {

        public AirtableDeleteBuilder.PublicApi setTable(String tableId) {
            AirtableDeleteBuilder.this.table = tableId;
            return AirtableDeleteBuilder.this.publicApi;
        }

    }

    public class PublicApi {

        public AirtableDeleteBuilder.PublicApi setIds(String... ids) {
            AirtableDeleteBuilder.this.ids.addAll(Arrays.asList(ids));
            return this;
        }

        public AirtableDeleteResponse execute() throws AirtableException {
            return AirtableDeleteBuilder.this.apiRef.execute(AirtableDeleteBuilder.this);
        }

    }

}
