package com.avihu.airtable.methods.operation;

import com.avihu.airtable.Airtable;
import com.avihu.airtable.exception.AirtableException;
import com.avihu.airtable.methods.base.AirtableRequestBuilderWithClass;
import com.avihu.airtable.methods.extra.AirtableSortField;
import com.avihu.airtable.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable.methods.annotation.AirtableBuilderQuery;
import com.avihu.airtable.methods.annotation.AirtableBuilderQueryList;
import com.avihu.airtable.model.get.external.AirtableGetIterator;
import com.avihu.airtable.model.get.external.AirtableGetResponse;
import com.avihu.airtable.model.get.external.AirtableRecordGetResponse;
import com.avihu.airtable.model.get.internal.AirtableInitialGetResponse;
import com.avihu.airtable.reflection.AirtableObjectMapper;
import kong.unirest.HttpMethod;

import java.util.*;

public class AirtableGetBuilder extends AirtableRequestBuilderWithClass<AirtableInitialGetResponse, AirtableGetResponse> {

    private final Airtable.InternalApi apiRef;
    private final AirtableGetBuilder.PublicApi publicApi;
    private final AirtableGetBuilder.DatabaseSetter databaseSetter;
    private final AirtableGetBuilder.TableSetter tableSetter;
    private Class<?> toClass;

    @AirtableBuilderPath(0)
    private String database;
    @AirtableBuilderPath(1)
    private String table;
    @AirtableBuilderQuery("maxRecords")
    private Integer maxRecords;
    @AirtableBuilderQuery("pageSize")
    private Integer pageSize = 20;
    @AirtableBuilderQuery("filterByFormula")
    private String filterByFormula;
    @AirtableBuilderQuery("view")
    private String view;
    @AirtableBuilderQuery("offset")
    private String offset;
    @AirtableBuilderQueryList("fields")
    private Set<String> fields;
    @AirtableBuilderQueryList("sort")
    private Set<AirtableSortField> sort;

    private AirtableGetBuilder(Airtable.InternalApi apiRef) {
        this.apiRef = apiRef;
        this.publicApi = new PublicApi();
        this.databaseSetter = new DatabaseSetter();
        this.tableSetter = new TableSetter();
    }

    public static AirtableGetBuilder.DatabaseSetter buildInstance(Airtable.InternalApi apiRef) {
        return new AirtableGetBuilder(apiRef).databaseSetter;
    }

    @Override
    public Class<AirtableInitialGetResponse> getInitialResponseClass() {
        return AirtableInitialGetResponse.class;
    }

    @Override
    public AirtableGetResponse transformInitial(AirtableInitialGetResponse source) {
        AirtableGetResponse toReturn = new AirtableGetResponse(source);
        List<AirtableRecordGetResponse> parsedRecords = new ArrayList<>();
        source.getRecords().forEach(record -> parsedRecords.add(new AirtableRecordGetResponse(record)));
        toReturn.setRecords(parsedRecords);
        return toReturn;
    }

    @Override
    public <T> List<T> transformToRequiredClass(AirtableGetResponse source) throws AirtableException {
        if (this.toClass.equals(source.getClass())) {
            return (List<T>) source.getRecords();
        }
        List<T> toReturn = new ArrayList<>();
        for (AirtableRecordGetResponse record : source.getRecords()) {
            T object = (T) AirtableObjectMapper.toObject(record, this.toClass);
            toReturn.add(object);
        }
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

    public PublicApi getPublicApi() {
        return publicApi;
    }

    public class DatabaseSetter {

        public AirtableGetBuilder.TableSetter setDatabase(String databaseId) {
            AirtableGetBuilder.this.database = databaseId;
            return AirtableGetBuilder.this.tableSetter;
        }

    }

    public class TableSetter {

        public AirtableGetBuilder.PublicApi setTable(String tableId) {
            AirtableGetBuilder.this.table = tableId;
            return AirtableGetBuilder.this.publicApi;
        }

    }

    public class PublicApi {

        public AirtableGetBuilder.PublicApi setMaxRecords(int maxRecords) {
            AirtableGetBuilder.this.maxRecords = maxRecords;
            return this;
        }

        public AirtableGetBuilder.PublicApi setFilterByFormula(String filterByFormula) {
            AirtableGetBuilder.this.filterByFormula = filterByFormula;
            return this;
        }

        public AirtableGetBuilder.PublicApi setView(String view) {
            AirtableGetBuilder.this.view = view;
            return this;
        }

        public AirtableGetBuilder.PublicApi setFilterByFields(String... fields) {
            AirtableGetBuilder.this.fields = new HashSet<>();
            AirtableGetBuilder.this.fields.addAll(Arrays.asList(fields));
            return this;
        }

        public AirtableGetBuilder.PublicApi addFilterByFields(String... fields) {
            if (AirtableGetBuilder.this.fields == null) {
                AirtableGetBuilder.this.fields = new HashSet<>();
            }
            AirtableGetBuilder.this.fields.addAll(Arrays.asList(fields));
            return this;
        }

        public AirtableGetBuilder.PublicApi setSortByFields(AirtableSortField... fields) {
            AirtableGetBuilder.this.sort = new HashSet<>();
            AirtableGetBuilder.this.sort.addAll(Arrays.asList(fields));
            return this;
        }

        public AirtableGetBuilder.PublicApi addSortByFields(AirtableSortField... fields) {
            if (AirtableGetBuilder.this.sort == null) {
                AirtableGetBuilder.this.sort = new HashSet<>();
            }
            AirtableGetBuilder.this.sort.addAll(Arrays.asList(fields));
            return this;
        }

        public AirtableGetBuilder.PublicApi setOffset(String offset) {
            AirtableGetBuilder.this.offset = offset;
            return this;
        }

        public AirtableGetBuilder.PublicApi setIterationPageSize(int pageSize) {
            AirtableGetBuilder.this.pageSize = pageSize;
            return this;
        }

        public <T> List<T> execute(Class<T> toClass) throws AirtableException {
            AirtableGetBuilder.this.toClass = toClass;
            return AirtableGetBuilder.this.apiRef.executeToClass(AirtableGetBuilder.this);
        }

        public AirtableGetResponse execute() throws AirtableException {
            return AirtableGetBuilder.this.apiRef.execute(AirtableGetBuilder.this);
        }

        public AirtableGetIterator<AirtableGetResponse> iterate() {
            return this.iterate(AirtableGetResponse.class);
        }

        public <T> AirtableGetIterator<T> iterate(Class<T> toClass) {
            AirtableGetBuilder.this.toClass = toClass;
            return new AirtableGetIterator<>(AirtableGetBuilder.this.apiRef, AirtableGetBuilder.this);
        }

    }

}
