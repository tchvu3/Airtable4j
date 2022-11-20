package com.avihu.airtable.methods.operation;

import com.avihu.airtable.Airtable;
import com.avihu.airtable.exception.AirtableException;
import com.avihu.airtable.methods.base.AirtableRequestBuilderWithClass;
import com.avihu.airtable.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable.model.get.external.AirtableRecordGetResponse;
import com.avihu.airtable.model.get.internal.AirtableInitialRecordGetResponse;
import com.avihu.airtable.reflection.AirtableObjectMapper;
import kong.unirest.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class AirtableGetSingleBuilder extends AirtableRequestBuilderWithClass<AirtableInitialRecordGetResponse, AirtableRecordGetResponse> {

    private final Airtable.InternalApi apiRef;
    private final AirtableGetSingleBuilder.PublicApi publicApi;
    private final AirtableGetSingleBuilder.DatabaseSetter databaseSetter;
    private final AirtableGetSingleBuilder.TableSetter tableSetter;
    private Class<?> toClass;

    @AirtableBuilderPath(0)
    private String database;
    @AirtableBuilderPath(1)
    private String table;
    @AirtableBuilderPath(2)
    private String rowId;

    private AirtableGetSingleBuilder(Airtable.InternalApi apiRef) {
        this.apiRef = apiRef;
        this.publicApi = new PublicApi();
        this.databaseSetter = new DatabaseSetter();
        this.tableSetter = new TableSetter();
    }

    public static AirtableGetSingleBuilder.DatabaseSetter buildInstance(Airtable.InternalApi apiRef) {
        return new AirtableGetSingleBuilder(apiRef).databaseSetter;
    }

    @Override
    public Class<AirtableInitialRecordGetResponse> getInitialResponseClass() {
        return AirtableInitialRecordGetResponse.class;
    }

    @Override
    public AirtableRecordGetResponse transformInitial(AirtableInitialRecordGetResponse source) {
        return new AirtableRecordGetResponse(source);
    }

    @Override
    public <T> List<T> transformToRequiredClass(AirtableRecordGetResponse source) throws AirtableException {
        List<T> toReturn = new ArrayList<>();
        toReturn.add((T) AirtableObjectMapper.toObject(source, this.toClass));
        return toReturn;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Object getRequestBody() {
        return null;
    }

    public class DatabaseSetter {

        public AirtableGetSingleBuilder.TableSetter setDatabase(String databaseId) {
            AirtableGetSingleBuilder.this.database = databaseId;
            return AirtableGetSingleBuilder.this.tableSetter;
        }

    }

    public class TableSetter {

        public AirtableGetSingleBuilder.PublicApi setTable(String tableId) {
            AirtableGetSingleBuilder.this.table = tableId;
            return AirtableGetSingleBuilder.this.publicApi;
        }

    }

    public class PublicApi {

        public AirtableGetSingleBuilder.PublicApi setRowId(String rowId) {
            AirtableGetSingleBuilder.this.rowId = rowId;
            return this;
        }

        public <T> T execute(Class<T> toClass) throws AirtableException {
            AirtableGetSingleBuilder.this.toClass = toClass;
            return (T) AirtableGetSingleBuilder.this.apiRef.executeToClass(AirtableGetSingleBuilder.this).get(0);
        }

        public AirtableRecordGetResponse execute() throws AirtableException {
            return AirtableGetSingleBuilder.this.apiRef.execute(AirtableGetSingleBuilder.this);
        }
    }

}
