package com.avihu.airtable.methods.base;

import com.avihu.airtable.exception.AirtableException;

import java.util.List;

public abstract class AirtableRequestBuilderWithClass<I, E> extends AirtableRequestBuilder<I, E> {

    public abstract <T> List<T> transformToRequiredClass(E source) throws AirtableException;

}
