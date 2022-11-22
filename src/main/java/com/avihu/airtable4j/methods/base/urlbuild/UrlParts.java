package com.avihu.airtable4j.methods.base.urlbuild;

import com.avihu.airtable4j.exception.AirtableException;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderPath;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderQuery;
import com.avihu.airtable4j.methods.annotation.AirtableBuilderQueryList;
import com.avihu.airtable4j.methods.base.AirtableRequestBuilder;
import com.avihu.airtable4j.utils.AirtableGenericUtils;
import com.avihu.airtable4j.utils.AirtableTypingUtils;

import java.lang.reflect.Field;
import java.util.*;

public class UrlParts {

    private final AirtableRequestBuilder<?, ?> airtableRequestBuilderRef;
    private final List<CustomField> pathParams = new ArrayList<>();
    private final List<CustomField> queryParams = new ArrayList<>();
    private final List<CustomField> queryListParams = new ArrayList<>();

    public UrlParts(AirtableRequestBuilder<?, ?> airtableRequestBuilderRef) {
        this.airtableRequestBuilderRef = airtableRequestBuilderRef;
    }

    public List<CustomField> getPathParams() {
        return this.pathParams;
    }

    public void addPathParams(Field field) throws AirtableException {
        Object fieldValue = this.getFieldValue(field);
        boolean required = field.getAnnotation(AirtableBuilderPath.class).required();
        CustomField toAdd = new CustomField(field, fieldValue, required, field.getName());
        if (toAdd.isRequiredButInvalidAsString()) {
            throw new AirtableException("Required path param " + toAdd.getFieldName() + " is missing.");
        } else if (toAdd.isValueValidAsString()) {
            this.pathParams.add(toAdd);
        }
    }

    public void sortPathParams() {
        this.pathParams.sort(Comparator.comparingInt(field -> field.getField().getAnnotation(AirtableBuilderPath.class).value()));
    }

    public List<CustomField> getQueryParams() {
        return this.queryParams;
    }

    public void addQueryParams(Field field) throws AirtableException {
        Object fieldValue = this.getFieldValue(field);
        AirtableBuilderQuery annotation = field.getAnnotation(AirtableBuilderQuery.class);
        CustomField toAdd = new CustomField(field, fieldValue, annotation.required(), annotation.value());
        if (toAdd.isRequiredButInvalidAsString()) {
            throw new AirtableException("Required query param " + toAdd.getFieldName() + " is missing.");
        } else if (toAdd.isValueValidAsString()) {
            this.queryParams.add(toAdd);
        }
    }

    public List<CustomField> getQueryListParams() {
        return this.queryListParams;
    }

    public void addQueryListParams(Field field) throws AirtableException {
        Object fieldValue = this.getFieldValue(field);
        AirtableBuilderQueryList annotation = field.getAnnotation(AirtableBuilderQueryList.class);
        CustomField toAdd = new CustomField(field, fieldValue, annotation.required(), annotation.value());
        Collection<?> queryListParam = (Collection<?>) toAdd.getValue();
        boolean invalidQueryListParamValue = AirtableTypingUtils.isNotCollectionOrEmpty(queryListParam) || queryListParam.stream().noneMatch(Objects::nonNull);
        if (toAdd.isRequired() && invalidQueryListParamValue) {
            throw new AirtableException("Required query list param " + toAdd.getFieldName() + " is missing.");
        } else if (!invalidQueryListParamValue) {
            this.queryListParams.add(toAdd);
        }
    }

    public boolean havingQueryParams() {
        return this.getQueryParams().size() > 0 || this.getQueryListParams().size() > 0;
    }


    private Object getFieldValue(Field field) {
        return AirtableGenericUtils.getFieldValue(field, this.airtableRequestBuilderRef);
    }

}
