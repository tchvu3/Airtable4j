package com.avihu.airtable4j;

import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.exception.AirtableRuntimeException;
import com.avihu.airtable4j.methods.base.AirtableRequestBuilder;
import com.avihu.airtable4j.methods.base.AirtableRequestBuilderWithClass;
import com.avihu.airtable4j.methods.operation.*;
import com.avihu.airtable4j.types.servererror.AirtableComplexServerError;
import com.avihu.airtable4j.types.servererror.AirtableSimpleServerError;
import com.google.gson.Gson;
import kong.unirest.*;

import java.util.List;
import java.util.concurrent.Callable;

public class Airtable {

    private final static String EXTERNAL_API_KEY = "AIRTABLE4J_API_KEY";
    private final static String EXTERNAL_BASE_URL_KEY = "AIRTABLE4J_BASE_URL";
    private final static String DEFAULT_BASE_URL = "https://api.airtable.com/v0";

    private final String apiKey;
    private final String database;
    private final String table;
    private final String baseUrl;

    private final CustomPublicApi customPublicApi;
    private final PublicApi publicApi;
    private final InternalApi internalApi;

    private Airtable(String apiKey, String database, String table) {
        Unirest.config().cookieSpec(CookieSpecs.STANDARD);
        this.apiKey = apiKey;
        this.database = database;
        this.table = table;
        this.customPublicApi = new CustomPublicApi();
        this.internalApi = new InternalApi();
        this.publicApi = new PublicApi();
        String baseUrlFromExternalSource = Airtable.tryToFetchBaseUrlFromExternalSource();
        this.baseUrl = (baseUrlFromExternalSource == null) ? Airtable.DEFAULT_BASE_URL : baseUrlFromExternalSource;
    }

    public static CustomPublicApi buildInstance() {
        return new Airtable(Airtable.tryToFetchApiKeyFromExternalSource(), null, null).customPublicApi;
    }

    public static PublicApi buildInstance(String database, String table) {
        return new Airtable(Airtable.tryToFetchApiKeyFromExternalSource(), database, table).publicApi;
    }

    public static CustomPublicApi buildInstance(String apiKey) {
        return new Airtable(apiKey, null, null).customPublicApi;
    }

    public static PublicApi buildInstance(String apiKey, String database, String table) {
        return new Airtable(apiKey, database, table).publicApi;
    }

    private static String tryToFetchApiKeyFromExternalSource() {
        String propertyValue = tryToFetchValueFromExternalSource(Airtable.EXTERNAL_API_KEY);
        if (propertyValue != null) {
            return propertyValue;
        }
        throw new AirtableRuntimeException("Could not find the Airtable API key.");
    }

    private static String tryToFetchBaseUrlFromExternalSource() {
        return tryToFetchValueFromExternalSource(Airtable.EXTERNAL_BASE_URL_KEY);
    }

    private static String tryToFetchValueFromExternalSource(String key) {
        String propertyValue = System.getProperty(key);
        if (propertyValue != null && propertyValue.trim().length() > 0) {
            return propertyValue.trim();
        }
        String envVariable = System.getenv(key);
        if (envVariable != null && envVariable.trim().length() > 0) {
            return envVariable.trim();
        }
        return null;
    }

    public class PublicApi {

        public AirtableGetBuilder.PublicApi get() {
            return AirtableGetBuilder.buildInstance(Airtable.this.internalApi).setDatabase(Airtable.this.database).setTable(Airtable.this.table);
        }

        public AirtableGetSingleBuilder.PublicApi getSingle() {
            return AirtableGetSingleBuilder.buildInstance(Airtable.this.internalApi).setDatabase(Airtable.this.database).setTable(Airtable.this.table);
        }

        public <T> AirtableAddBuilder<T>.PublicApi add() {
            return AirtableAddBuilder.<T>buildInstance(Airtable.this.internalApi).setDatabase(Airtable.this.database).setTable(Airtable.this.table);
        }

        public <T> AirtableUpdateBuilder<T>.PublicApi update() {
            return AirtableUpdateBuilder.<T>buildInstance(Airtable.this.internalApi).setDatabase(Airtable.this.database).setTable(Airtable.this.table);
        }

        public <T> AirtableReplaceBuilder<T>.PublicApi replace() {
            return AirtableReplaceBuilder.<T>buildInstance(Airtable.this.internalApi).setDatabase(Airtable.this.database).setTable(Airtable.this.table);
        }

        public AirtableDeleteBuilder.PublicApi delete() {
            return AirtableDeleteBuilder.buildInstance(Airtable.this.internalApi).setDatabase(Airtable.this.database).setTable(Airtable.this.table);
        }

    }

    public class CustomPublicApi {

        public AirtableGetBuilder.DatabaseSetter get() {
            return AirtableGetBuilder.buildInstance(Airtable.this.internalApi);
        }

        public AirtableGetSingleBuilder.DatabaseSetter getSingle() {
            return AirtableGetSingleBuilder.buildInstance(Airtable.this.internalApi);
        }

        public <T> AirtableAddBuilder<T>.DatabaseSetter add() {
            return AirtableAddBuilder.buildInstance(Airtable.this.internalApi);
        }

        public <T> AirtableUpdateBuilder<T>.DatabaseSetter update() {
            return AirtableUpdateBuilder.buildInstance(Airtable.this.internalApi);
        }

        public <T> AirtableReplaceBuilder<T>.DatabaseSetter replace() {
            return AirtableReplaceBuilder.buildInstance(Airtable.this.internalApi);
        }

        public AirtableDeleteBuilder.DatabaseSetter delete() {
            return AirtableDeleteBuilder.buildInstance(Airtable.this.internalApi);
        }

    }

    public class InternalApi {

        public <I, E> E execute(AirtableRequestBuilder<I, E> builder) throws AirtableException {
            return this.catchExceptions(() -> {
                HttpResponse<String> httpResponse = this.prepareAndExecuteHttpRequest(builder);
                throwExceptionOnInvalidHttpResponse(httpResponse);
                I response = new Gson().fromJson(httpResponse.getBody(), builder.getInitialResponseClass());
                return builder.transformInitial(response);
            });
        }

        public <I, E, R> List<R> executeToClass(AirtableRequestBuilderWithClass<I, E> builder) throws AirtableException {
            return this.catchExceptions(() -> builder.transformToRequiredClass(this.execute(builder)));
        }

        private <T> T catchExceptions(Callable<T> callable) throws AirtableException {
            try {
                return callable.call();
            } catch (AirtableException exception) {
                throw exception;
            } catch (Exception exception) {
                throw new AirtableException(exception);
            }
        }

        private <I, E> HttpResponse<String> prepareAndExecuteHttpRequest(AirtableRequestBuilder<I, E> builder) throws AirtableException {
            String url = builder.buildUrl(Airtable.this.baseUrl);
            HttpRequestWithBody request = Unirest.request(builder.getMethod().name(), url).header(HeaderNames.AUTHORIZATION, "Bearer " + Airtable.this.apiKey);
            Object requestBody = builder.getRequestBody();
            if (requestBody == null) {
                return request.asString();
            }
            request.header(HeaderNames.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            return request.body(new Gson().toJson(requestBody)).asString();
        }

        private void throwExceptionOnInvalidHttpResponse(HttpResponse<String> httpResponse) throws AirtableException {
            if (httpResponse == null) {
                throw new AirtableException("HTTP Response object is null! please check your input and your internet connection.");
            }
            if (httpResponse.isSuccess() && httpResponse.getBody() == null) {
                throw new AirtableException("The HTTP request was a success, but no response body is present.");
            }
            if (!httpResponse.isSuccess() && httpResponse.getBody() != null) {
                AirtableComplexServerError airtableComplexServerError = this.responseBodyToComplexServerError(httpResponse);
                if (airtableComplexServerError != null) {
                    throw new AirtableException("The request errored with: " + httpResponse.getBody(), airtableComplexServerError);
                }
                AirtableSimpleServerError airtableSimpleServerError = this.responseBodyToSimpleServerError(httpResponse);
                if (airtableSimpleServerError != null) {
                    throw new AirtableException("The request errored with: " + httpResponse.getBody(), airtableSimpleServerError);
                }
                throw new AirtableException("The request errored with: " + httpResponse.getBody());
            }
        }

        private AirtableComplexServerError responseBodyToComplexServerError(HttpResponse<String> httpResponse) {
            try {
                return new Gson().fromJson(httpResponse.getBody(), AirtableComplexServerError.class);
            } catch (Exception ignore) {
                return null;
            }
        }

        private AirtableSimpleServerError responseBodyToSimpleServerError(HttpResponse<String> httpResponse) {
            try {
                return new Gson().fromJson(httpResponse.getBody(), AirtableSimpleServerError.class);
            } catch (Exception ignore) {
                return null;
            }
        }

    }

}
