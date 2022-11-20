package com.avihu.airtable.methods.base;

import com.avihu.airtable.exception.AirtableException;
import com.avihu.airtable.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable.methods.annotation.AirtableBuilderQuery;
import com.avihu.airtable.methods.annotation.AirtableBuilderQueryList;
import com.avihu.airtable.methods.base.urlbuild.CustomField;
import com.avihu.airtable.methods.base.urlbuild.UrlParts;
import com.avihu.airtable.methods.base.urlbuild.UrlStringBuilder;
import kong.unirest.HttpMethod;

import java.lang.reflect.Field;

public abstract class AirtableRequestBuilder<I, E> {

    public String buildUrl(String baseUrl) throws AirtableException {
        try {
            UrlParts urlParts = this.getUrlParts();
            UrlStringBuilder builder = new UrlStringBuilder(baseUrl, urlParts);

            for (CustomField pathParam : urlParts.getPathParams()) {
                builder.appendPathParam(pathParam);
            }

            builder.appendQueryParamsSeparatorIfNeeded();

            for (CustomField queryParam : urlParts.getQueryParams()) {
                builder.appendQueryParam(queryParam);
            }

            for (CustomField queryListParam : urlParts.getQueryListParams()) {
                builder.appendQueryListParam(queryListParam);
            }

            return builder.toUrlString();
        } catch (AirtableException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new AirtableException(exception);
        }
    }

    private UrlParts getUrlParts() throws AirtableException {
        UrlParts toReturn = new UrlParts(this);
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(AirtableBuilderPath.class)) {
                toReturn.addPathParams(field);
            } else if (field.isAnnotationPresent(AirtableBuilderQuery.class)) {
                toReturn.addQueryParams(field);
            } else if (field.isAnnotationPresent(AirtableBuilderQueryList.class)) {
                toReturn.addQueryListParams(field);
            }
        }
        toReturn.sortPathParams();
        return toReturn;
    }

    public abstract Class<I> getInitialResponseClass();

    public abstract E transformInitial(I source);

    public abstract HttpMethod getMethod();

    public abstract Object getRequestBody();


}
