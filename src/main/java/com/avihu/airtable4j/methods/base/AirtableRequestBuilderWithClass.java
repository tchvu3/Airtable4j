package com.avihu.airtable4j.methods.base;

import com.avihu.airtable4j.exception.AirtableException;

import java.util.List;

public abstract class AirtableRequestBuilderWithClass<I, E> extends AirtableRequestBuilder<I, E> {

    public abstract <T> List<T> transformToRequiredClass(E source) throws AirtableException;

}
